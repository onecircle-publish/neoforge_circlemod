package com.circle.circlemod.core.resource.item.chest_shield;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

import java.util.ArrayList;


public class ChestShield extends ShieldItem {
    public ChestShield() {
        super(new Item.Properties().durability(336)
                .component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY).component(DataComponents.CONTAINER, ItemContainerContents.fromItems(new ArrayList<ItemStack>())));
    }
}
