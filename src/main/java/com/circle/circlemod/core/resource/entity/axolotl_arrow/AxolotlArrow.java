package com.circle.circlemod.core.resource.entity.axolotl_arrow;

import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.CircleResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;


public class AxolotlArrow extends AbstractArrow {
    public AxolotlArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super((EntityType<? extends AbstractArrow>) CircleUniRegister.ENTITIES_REGISTRY.get(CircleResourceLocation.AXOLOTL_ARROW)
                                                                                       .get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    public AxolotlArrow(EntityType<?> entityType, Level level) {
        super((EntityType<? extends AbstractArrow>) CircleUniRegister.ENTITIES_REGISTRY.get(CircleResourceLocation.AXOLOTL_ARROW)
                                                                                       .get(), level);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return null;
    }
}
