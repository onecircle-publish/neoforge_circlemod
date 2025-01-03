package com.circle.circlemod.core.event;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.CircleResourceLocation;
import com.circle.circlemod.core.resource.item.chest_shield.ChestShield;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerDestroyItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = CircleMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ItemEvents implements IModBusEvent {

    @SubscribeEvent
    public static void itemUseEvent(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity target = event.getTarget();
        Player entity = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        if (target instanceof Axolotl) {
            DeferredHolder<Item, Item> axoltlSword = CircleUniRegister.ITEMS_REGISTRY.get(CircleResourceLocation.AXOLOTL_SWORD);
            DeferredHolder<Item, Item> axoltlBow = CircleUniRegister.ITEMS_REGISTRY.get(CircleResourceLocation.AXOLOTL_BOW);
            DeferredHolder<Item, Item> axoltlPickaxe = CircleUniRegister.ITEMS_REGISTRY.get(CircleResourceLocation.AXOLOTL_PICKAXE);
            Item specialItem = null;
            if (itemStack.is(Items.IRON_SWORD)) {
                entity.setItemInHand(entity.getUsedItemHand(), new ItemStack(axoltlSword.get()));
                specialItem = axoltlSword.get();
            } else if (itemStack.is(Items.BOW)) {
                // 清空当前选中的物品
                entity.setItemInHand(entity.getUsedItemHand(), new ItemStack(axoltlBow.get()));
                specialItem = axoltlBow.get();
            } else if (itemStack.is(Items.IRON_PICKAXE)) {
                entity.setItemInHand(entity.getUsedItemHand(), new ItemStack(axoltlPickaxe.get()));
                specialItem = axoltlPickaxe.get();
            }


            if (specialItem != null) {
                target.discard();
            }
        }
    }


}
