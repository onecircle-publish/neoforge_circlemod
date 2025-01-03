package com.circle.circlemod.core.resource.item.chest_shield;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.entity.BannerPatternLayers;


public class ChestShield extends ShieldItem {
    public ChestShield() {
        super(new Item.Properties().durability(336)
                                   .component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY));
    }
}
