package com.circle.circlemod.core.cability;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AxolotlCability {

    /**
     * 修复延迟 默认60tick
     */
    private int repireDelay;
    /**
     * 修复计时器
     */
    private int repairTimer = 0;

    public AxolotlCability() {
        this(60);
    }

    public AxolotlCability(int repireDelay) {
        this.repireDelay = repireDelay;
    }


    /**
     * 在 inventoryTick 中使用，每次物品栏tick的时候就会执行
     *
     * @param level 水平
     * @param stack 叠
     */
    public void repire(Level level, ItemStack stack) {
        if (!level.isClientSide) {
            repairTimer++;

            if (repairTimer > repireDelay) {
                if (stack.getMaxDamage() > stack.getDamageValue()) {
                    stack.setDamageValue(stack.getDamageValue() - 1);
                    repairTimer = 0;
                }
            }
        }
    }

    /**
     * 刷新修复计时器
     */
    public void refreshRepairTimer() {
        repairTimer = 0;
    }
}
