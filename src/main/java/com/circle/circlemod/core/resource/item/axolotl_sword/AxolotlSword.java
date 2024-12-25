package com.circle.circlemod.core.resource.item.axolotl_sword;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.cability.AxolotlCability;
import com.circle.circlemod.core.resource.CircleResourceLocation;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class AxolotlSword extends SwordItem {
    public AxolotlCability cability = new AxolotlCability();

    public AxolotlSword() {
        super(Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.WOOD, 1, -2.4F)));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 2;
    }

    public static void registerItemProperty() {
        ItemProperties.register(CircleUniRegister.ITEMS_REGISTRY.get(CircleResourceLocation.AXOLOTL_SWORD)
                                                                .get(), ResourceLocation.fromNamespaceAndPath(CircleMod.MODID, "count"), (itemStack, clientWorld, livingEntity, num) -> {
            int damageValue = itemStack.getDamageValue();
            if (damageValue == 0) return 0;
            else return 1;
        });
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        cability.repire(level, stack);
    }


    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        if (stack.getMaxDamage() - stack.getDamageValue() > 1) return 1;
        return 0;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        cability.refreshRepairTimer();
        return super.hurtEnemy(stack, target, attacker);
    }
}
