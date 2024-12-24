package com.circle.circlemod.core.builds.register;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.resource.CircleResource;
import com.circle.circlemod.core.resource.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.function.Supplier;

public class CircleUniRegister {
    public static HashMap<ResourceLocation, DeferredHolder<Item, Item>> ITEMS_REGISTRY = new HashMap<ResourceLocation,DeferredHolder<Item, Item>>();

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, CircleMod.MODID);
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, CircleMod.MODID);

    public CircleUniRegister(IEventBus eventBus) {
        new CircleResource();
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
    }

    /**
     * 注册项目
     *
     * @return {@link DeferredRegister }<{@link Item }>
     */
    public static DeferredHolder<Item, Item> registerItem(ResourceLocation resource, Supplier<Item> supplier) {
        DeferredHolder<Item, Item> register = ITEMS.register(resource.key, supplier);
        ITEMS_REGISTRY.put(resource,register);
        return register;
    }
}
