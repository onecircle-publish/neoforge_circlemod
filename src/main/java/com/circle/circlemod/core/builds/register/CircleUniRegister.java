package com.circle.circlemod.core.builds.register;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.resource.CircleResource;
import com.circle.circlemod.core.resource.CircleResourceLocation;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class CircleUniRegister {
    /**
     * 项目注册表
     */
    public static HashMap<CircleResourceLocation, DeferredHolder<Item, Item>> ITEMS_REGISTRY = new HashMap<>();
    /**
     * 组件注册表
     */
    public static HashMap<String, DeferredHolder<DataComponentType<?>, DataComponentType<?>>> COMPONENTS_REGISTRY = new HashMap<>();

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, CircleMod.MODID);
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, CircleMod.MODID);

    /**
     * 数组组件，用于给ItemStack绑定持久化数据
     */
    public static DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, CircleMod.MODID);


    public CircleUniRegister(IEventBus eventBus) {
        new CircleResource();
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        DATA_COMPONENTS.register(eventBus);
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

    /**
     * 注册数据组件
     *
     * @param name    名字
     * @param builder 建筑工人
     * @return {@link DeferredHolder }<{@link DataComponentType }<{@link ? }>, {@link DataComponentType }<{@link T }>>
     */
    public static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> registerDataComponents(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        DeferredHolder<DataComponentType<?>, DataComponentType<T>> register = DATA_COMPONENTS.register(name, () -> builder.apply(DataComponentType.builder())
                                                                                                                          .build());


        return register;
    }

}
