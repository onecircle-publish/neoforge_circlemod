package com.circle.circlemod.core.builds.register;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.resource.CircleResource;
import com.circle.circlemod.core.resource.CircleResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.function.Supplier;

public class CircleUniRegister {
    /**
     * 项目注册表
     */
    public static HashMap<CircleResourceLocation, DeferredHolder<Item, Item>> ITEMS_REGISTRY = new HashMap<>();
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, CircleMod.MODID);
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, CircleMod.MODID);
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CircleMod.MODID);


    /**
     * 注册创意模式选项卡
     */
    public void registerCreativeModeTabs() {
        CREATIVE_MODE_TABS.register("circle", () -> CreativeModeTab.builder()
                                                                   .title(Component.translatable("itemGroup." + CircleMod.MODID + ".creative"))
                                                                   .icon(() -> new ItemStack(CircleUniRegister.ITEMS_REGISTRY.get(CircleResourceLocation.GOLD_STAFF)))
                                                                   .displayItems(((parameters, output) -> {
                                                                       CircleUniRegister.ITEMS.getEntries()
                                                                                              .forEach(item -> {
                                                                                                  output.accept(item.get());
                                                                                              });
                                                                   }))
                                                                   .build());
    }


    public CircleUniRegister(IEventBus eventBus) {
        new CircleResource();
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        CREATIVE_MODE_TABS.register(eventBus);
        registerCreativeModeTabs();

    }

    /**
     * 注册项目
     *
     * @return {@link DeferredRegister }<{@link Item }>
     */
    public static DeferredHolder<Item, Item> registerItem(CircleResourceLocation resource, Supplier<Item> supplier) {
        DeferredHolder<Item, Item> register = ITEMS.register(resource.key, supplier);
        ITEMS_REGISTRY.put(resource, register);


        return register;
    }
}
