package com.circle.circlemod.core.event;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.resource.item.chest_shield.ChestShield;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : yuanxin
 * @date : 2025-01-03 17:27
 **/

@EventBusSubscriber(modid = CircleMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class CircleLivingEntityEvent {

    @SubscribeEvent
    public static void onLivingEntityHurt(LivingShieldBlockEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack useItem = entity.getUseItem();
        if (useItem.getItem() instanceof ChestShield) {

            ItemContainerContents itemContainerContents = useItem.getComponents()
                                                                 .get(DataComponents.CONTAINER);
            Entity directEntity = event.getDamageSource()
                                       .getDirectEntity();

            if (directEntity instanceof Arrow) {

                List<ItemStack> list = itemContainerContents.stream()
                                                            .toList();
                ArrayList<ItemStack> itemStacks = new ArrayList<>();
                itemStacks.addAll(list);
                itemStacks.add(new ItemStack(Items.ARROW));
                useItem.applyComponents(DataComponentPatch.builder()
                                                          .set(DataComponents.CONTAINER, ItemContainerContents.fromItems(itemStacks))
                                                          .build());

                directEntity.discard();
            }
        }
    }


}
