package com.circle.circlemod.core.resource.entity.axolotl_arrow;

import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.CircleResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;


public class AxolotlArrow extends AbstractArrow {
    public boolean isSafe = false;

    public AxolotlArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon, boolean isSafe) {
        super((EntityType<? extends AbstractArrow>) CircleUniRegister.ENTITIES_REGISTRY.get(CircleResourceLocation.AXOLOTL_ARROW)
                                                                                       .get(), owner, level, pickupItemStack, firedFromWeapon);
        this.isSafe = isSafe;
    }

    public AxolotlArrow(EntityType<?> entityType, Level level) {
        super((EntityType<? extends AbstractArrow>) CircleUniRegister.ENTITIES_REGISTRY.get(CircleResourceLocation.AXOLOTL_ARROW)
                                                                                       .get(), level);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return null;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        float health = 0f;
        if (entity instanceof LivingEntity) {
            health = ((LivingEntity) entity).getHealth();
        }

        if (isSafe && entity instanceof LivingEntity) {
            ((LivingEntity) entity).setHealth(health + 1);
            entity.hurt(level().damageSources()
                               .arrow(this, null), 0);

            if (!level().isClientSide) {
                ClientLevel level = Minecraft.getInstance().level;
                if (level != null) {
                    ParticleUtils.spawnParticles(level, entity.blockPosition(), 6, 1, 1, true, ParticleTypes.HEART);
                }
            }
        } else {
            super.onHitEntity(result);
        }
    }
}
