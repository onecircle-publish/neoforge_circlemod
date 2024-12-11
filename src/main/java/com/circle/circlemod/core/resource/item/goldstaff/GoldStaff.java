package com.circle.circlemod.core.resource.item.goldstaff;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GoldStaff extends Item {
    public GoldStaff() {
        super(new Item.Properties());
    }

    public GoldStaff(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        interactionTarget.hurt(interactionTarget
                .damageSources()
                .magic(), 10);

        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }
}
