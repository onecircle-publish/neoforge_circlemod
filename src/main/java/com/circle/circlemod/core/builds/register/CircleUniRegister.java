package com.circle.circlemod.core.builds.register;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.resource.CircleResource;
import com.circle.circlemod.core.resource.CircleResourceLocation;
import com.circle.circlemod.core.resource.entity.axolotl_arrow.AxolotlArrow;
import com.circle.circlemod.core.resource.entity.axolotl_arrow.AxolotlArrowRenderer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.SpectralArrowRenderer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.function.Supplier;

public class CircleUniRegister {
    /**
     * 项目注册表
     */
    public static HashMap<CircleResourceLocation, DeferredHolder<Item, Item>> ITEMS_REGISTRY = new HashMap<>();
    /**
     * 实体注册表
     */
    public static HashMap<CircleResourceLocation, DeferredHolder<EntityType<?>, EntityType<?>>> ENTITIES_REGISTRY = new HashMap<>();

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, CircleMod.MODID);
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, CircleMod.MODID);
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CircleMod.MODID);

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
        ENTITIES.register(eventBus);
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

    public static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<?>> registerEntity(CircleResourceLocation resource, EntityType.EntityFactory<T> factory, @Nullable EntityRendererProvider<T> provider, MobCategory category) {
        DeferredHolder<EntityType<?>, EntityType<?>> register = ENTITIES.register(resource.key, () -> {
            EntityType<T> build = EntityType.Builder.of(factory, category)
                                                    .sized(0.35F, 0.6F)
                                                    .eyeHeight(0.36F)
                                                    .ridingOffset(0.04F)
                                                    .clientTrackingRange(8)
                                                    .updateInterval(2)

                                                    .build(resource.key);
            if (provider != null) {
                EntityRenderers.register(build, provider);
            }
            return build;
        });

        ENTITIES_REGISTRY.put(resource, register);

        return register;
    }
}
