package com.circle.circlemod.core.resource.entity.vanilla_extends.item_entity;

import com.circle.circlemod.core.payload.SyncMovementPayload;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.function.Consumer;

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
    }


    @Override
    public void tick() {
        super.tick();

        Vec3 position = this.getPosition(1);
        Vec3 ownerPosition = owner.getPosition(1);

        // 旋转超过一圈，则保持移动到玩家，并尝试获取。
        if (useCount >= 1) {  // 超过一圈 移动到使用者
            if (position.distanceToSqr(ownerPosition) <= 10) {
                ItemStack itemstack = this.getItem();
                Item item = itemstack.getItem();

                if (owner.getInventory()
                         .add(itemstack)) {
                    owner.awardStat(Stats.ITEM_PICKED_UP.get(item), 1);
                    owner.take(this, 1);
                    owner.onItemPickup(this);
                    this.discard();
                    return;
                }
            }
        }

        // 碰到方块，则破坏
        Level level = this.level();
        BlockPos blockPos = new BlockPos((int) position.x, (int) position.y, (int) position.z);
        if (!level.getBlockState(blockPos)
                  .is(Blocks.AIR)) {
            level.destroyBlock(blockPos, true);
        }

        float radians = (float) Math.toRadians(angle);
        float sin = Mth.sin(radians);
        float cos = Mth.cos(radians);
        Vector3f normalize = new Vector3f(sin, 0, cos).normalize(radians);
        Vector3f target = new Vector3f(center.x + normalize.x, center.y + normalize.y, center.z + normalize.z);
        this.moveTo(new Vec3(target));
        angle = (angle + 18);
        if (angle >= 360) {
            useCount++;
        }
        angle = angle % 360;
        PacketDistributor.sendToAllPlayers(new SyncMovementPayload(this.getId(), center, radius, angle));

        if (useCount >= 1) {  // 超过一圈 移动到使用者
            this.moveTo(ownerPosition);
        }
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

    }

}

