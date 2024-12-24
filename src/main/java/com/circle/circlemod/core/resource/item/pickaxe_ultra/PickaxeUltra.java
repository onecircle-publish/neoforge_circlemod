package com.circle.circlemod.core.resource.item.pickaxe_ultra;

import com.circle.circlemod.core.resource.CircleResourceLocation;
import com.circle.circlemod.core.resource.entity.vanilla_extends.item_entity.CircleItemEntity;
import com.circle.circlemod.tools.StyleTool;
import com.circle.circlemod.tools.TranslateTool;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 升级版—铁镐
 *
 * @author yuanxin
 * @date 2024/12/14
 */
public class PickaxeUltra extends PickaxeItem {
    final int maxUseDuration = 72000;

    public PickaxeUltra() {
        super(Tiers.IRON,
                new Item.Properties().attributes(PickaxeItem.createAttributes(Tiers.IRON, 1.0F, -2.8F)));

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        TranslateTool.addItemTranslate(tooltipComponents, CircleResourceLocation.PICKAXE_ULTRA, CircleResourceLocation.TranslateKey.HOVER, StyleTool.getDefaultHoverFont());
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

    }

    /**
     * 释放后，移除当前手持物品，抛出一个物品实体
     *
     * @param stack
     * @param level
     * @param livingEntity
     * @param timeCharged
     */
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        Vec3 position = livingEntity.getPosition(1);

        if (!level.isClientSide) {
            // 移除该物品
            InteractionHand usedItemHand = livingEntity.getUsedItemHand();
            livingEntity.setItemInHand(usedItemHand, ItemStack.EMPTY);

            // 丢出物品实体
            CircleItemEntity itemEntity = new CircleItemEntity(level, position.x, position.y, position.z, stack, (Player) livingEntity);
            level.addFreshEntity(itemEntity);

        }
        ParticleUtils.spawnParticles(level, new BlockPos((int) position.x, (int) position.y, (int) position.z), 10, 1, 1, true, ParticleTypes.WAX_ON);
        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }


    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return maxUseDuration;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }


    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

}
