package com.circle.circlemod.core.resource.entity.vanilla_extends.item_entity;

import com.circle.circlemod.core.payload.SyncMovementPayload;
import com.circle.circlemod.tools.EnchantmentTool;
import com.mojang.logging.LogUtils;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 拓展原版ItemEntity（丢出去的物品实体）
 *
 * @author yuanxin
 * @date 2024/12/14
 */
public class CircleItemEntity extends ItemEntity {

    private static final Logger LOGGER = LogUtils.getLogger();
    double angle = 0;
    float radius = 3;
    Vector3f center = null;
    Player owner;
    ItemStack useItemStack;
    ArrayList<BlockState> tempBlockStateList = new ArrayList<>();

    /**
     * 丢出去的圈数
     */
    int useCount = 0;

    public CircleItemEntity(Level level, double x, double y, double z, ItemStack stack, @Nullable Player owner) {
        super(level, x, y, z, stack);
        this.setDefaultPickUpDelay();
        this.setNoGravity(true);
        this.setDeltaMovement(new Vec3(0, 0, 0));
        this.center = owner.getPosition(1)
                           .toVector3f();
        this.owner = owner;
        this.useItemStack = stack;
    }


    /**
     * 碰到方块时的逻辑
     * do destory（摧毁）
     */
    public void doDestory(Level level, Vec3 pos, BlockPos blockPos) {
        boolean isDrop = true;

        // 忠诚
        boolean loyalty = EnchantmentTool.hasThisEnchantment(useItemStack, Enchantments.LOYALTY) > 0;
        // 多重射击
        int multiShotLevel = EnchantmentTool.hasThisEnchantment(useItemStack, Enchantments.MULTISHOT);

        // 冲击附魔
        int punchLevel = EnchantmentTool.hasThisEnchantment(useItemStack, Enchantments.PUNCH);

        ArrayList<BlockPos> blocks = new ArrayList<>();
        if (punchLevel != 0) {
            for (int i = 0; i < punchLevel; i++) {
                for (int j = 0; j < punchLevel; j++) {
                    for (int z = 0; z <= multiShotLevel; z++) {
                        blocks.add(blockPos.offset(i, z, j));
                        blocks.add(blockPos.offset(-i, -z, -j));
                    }
                }
            }
        }

        blocks.add(blockPos);
        List<BlockPos> collect = blocks.stream()
                                       .distinct()
                                       .collect(Collectors.toList());
        blocks.clear();
        blocks.addAll(collect);

        // 如果没有忠诚则直接破坏，否则暂存起来，在收起物品的时候才获取物品
        if (loyalty) {
            isDrop = false;
        }

        for (BlockPos bPos : blocks) {
            BlockState blockState = level.getBlockState(bPos);
            tempBlockStateList.add(blockState);
            level.destroyBlock(bPos, isDrop);
        }
    }

    /**
     * 在绕圈完后，获取时触发
     */
    public void afterPickup() {
        tempBlockStateList.forEach(blockState -> {
            ItemEntity itemEntity = new ItemEntity(level(), owner.getX(), owner.getY(), owner.getZ(), blockState.getBlock()
                                                                                                                .asItem()
                                                                                                                .getDefaultInstance());
            level().addFreshEntity(itemEntity);
        });
        tempBlockStateList.clear();
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 position = this.getPosition(1);
        Vec3 ownerPosition = owner.getPosition(1);


        // 旋转超过一圈
        if (useCount > 1) {  // 超过一圈 移动到使用者
            this.moveTo(ownerPosition);
        } else {

            // region 碰到方块，则破坏
            Level level = this.level();
            BlockPos blockPos = new BlockPos((int) position.x, (int) position.y, (int) position.z);
            if (!level.getBlockState(blockPos)
                      .is(Blocks.AIR)) {

                doDestory(level, position, blockPos);
            }
            // endregion

            // region 绕圈旋转
            float radians = (float) Math.toRadians(angle);
            float sin = Mth.sin(radians);
            float cos = Mth.cos(radians);
            Vector3f normalize = new Vector3f(sin, 0, cos).normalize(radians);
            Vector3f target = new Vector3f(center.x + normalize.x, center.y + normalize.y, center.z + normalize.z);
            this.moveTo(new Vec3(target));
            angle = angle % 360;
            angle = (angle + 18);
            if (angle >= 360) {
                useCount++;
            }
            // endregion
        }
        // region 发包给客户端，更新位置数据
        PacketDistributor.sendToAllPlayers(new SyncMovementPayload(this.getId(), center, radius, angle));
    }

    @Override
    public SynchedEntityData getEntityData() {
        return super.getEntityData();
    }

    @Override
    public float getSpin(float partialTicks) {
        return 0;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(Vec3 vector) {

    }

    @Override
    public boolean isPickable() {
        return false;
    }


    @Override
    public boolean isColliding(BlockPos pos, BlockState state) {
        return false;
    }


    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public void playerTouch(Player entity) {
        if (useCount > 1) {
            super.playerTouch(entity);
            afterPickup();
        }
    }

}

