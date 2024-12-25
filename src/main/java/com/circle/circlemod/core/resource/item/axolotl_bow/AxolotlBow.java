package com.circle.circlemod.core.resource.item.axolotl_bow;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AxolotlBow extends BowItem {
    public AxolotlBow() {
        super(new Item.Properties().durability(2));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 2;
    }
}
