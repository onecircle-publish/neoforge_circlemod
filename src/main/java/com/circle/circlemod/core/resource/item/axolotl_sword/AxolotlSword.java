package com.circle.circlemod.core.resource.item.axolotl_sword;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;

/**
 * 美西螈剑
 *
 * @author yuanxin
 * @date 2024/12/23
 */
public class AxolotlSword extends SwordItem {

    /**
     * 修复延迟
     */
    private static final int REPAIR_DELAY = 60; // 3秒（60个tick）
    /**
     * 修复计时器
     */
    private int repairTimer = 0;

    public AxolotlSword(Tier p_tier, Properties p_properties, Tool toolComponentData) {
        super(p_tier, p_properties, toolComponentData);
    }

    public AxolotlSword(Tier tier, Properties properties) {
        super(tier, properties);
    }

    public AxolotlSword() {
        super(Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        repairTimer = 0;
        return super.hurtEnemy(stack, target, attacker);
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
