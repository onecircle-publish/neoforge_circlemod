package com.circle.circlemod.core.resource.item.axolotl_pickare;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;


/**
 * 美西螈镐
 *
 * @author yuanxin
 * @date 2024/12/23
 */
public class AxolotlPickaxe extends PickaxeItem {
    int count = 0;

    /**
     * 修复延迟
     */
    private static final int REPAIR_DELAY = 60; // 3秒（60个tick）
    /**
     * 修复计时器
     */
    private int repairTimer = 0;

    public AxolotlPickaxe() {
        super(Tiers.IRON, new Item.Properties().attributes(PickaxeItem.createAttributes(Tiers.IRON, 1, -2.1F)));
    }


    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        repairTimer = 0;
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!level.isClientSide) {
            repairTimer++;
            
            if (repairTimer > REPAIR_DELAY) {
                if (stack.getMaxDamage() > stack.getDamageValue()) {
                    stack.setDamageValue(stack.getDamageValue() - 1);
                    repairTimer = 0;
                }
            }
        }
    }


}
