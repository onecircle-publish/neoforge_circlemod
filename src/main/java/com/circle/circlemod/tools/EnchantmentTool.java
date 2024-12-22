package com.circle.circlemod.tools;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class EnchantmentTool {

    /**
     * 如果包含则返回附魔等级，否则返回0
     *
     * @param stack       叠
     * @param resourceKey 资源键
     * @return int 包含附魔则返回附魔等级，否则为0
     */
    public static int hasThisEnchantment(ItemStack stack, ResourceKey<Enchantment> resourceKey) {
        int level = 0;

        ItemEnchantments allEnchantments = stack.getAllEnchantments(null);

        for (Holder<Enchantment> enchantment : allEnchantments.keySet()) {
            if (enchantment.is(resourceKey)) {
                level = stack.getEnchantmentLevel(enchantment);
                break;
            }
        }

        return level;
    }
}
