package com.circle.circlemod.core.resource.item.axolotl_bow;

import com.circle.circlemod.core.cability.AxolotlCability;
import com.circle.circlemod.core.resource.entity.axolotl_arrow.AxolotlArrow;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class AxolotlBow extends BowItem {
    public static AxolotlCability cability = new AxolotlCability();

    public AxolotlBow() {
        super(new Item.Properties().durability(2));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 2;
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        cability.refreshRepairTimer();
        return super.useOnRelease(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        cability.repire(level, stack);
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        if (stack.getMaxDamage() - stack.getDamageValue() > 1) return 1;
        return 0;
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        AbstractArrow abstractarrow = new AxolotlArrow(level, shooter, ammo.copyWithCount(1), weapon, this.getMaxDamage(weapon) - this.getDamage(weapon) <= 1);
        if (isCrit) {
            abstractarrow.setCritArrow(true);
        }

        return customArrow(abstractarrow, ammo, weapon);
    }
}
