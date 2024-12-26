package com.circle.circlemod.core.resource.item.axolotl_bow;

import com.circle.circlemod.core.resource.entity.axolotl_arrow.AxolotlArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class AxolotlBow extends BowItem {
    public AxolotlBow() {
        super(new Item.Properties().durability(2));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 2;
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        AbstractArrow abstractarrow =new AxolotlArrow(level, shooter, ammo.copyWithCount(1), weapon);
        if (isCrit) {
            abstractarrow.setCritArrow(true);
        }

        return customArrow(abstractarrow, ammo, weapon);
    }
}
