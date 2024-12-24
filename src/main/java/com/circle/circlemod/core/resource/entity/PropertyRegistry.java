package com.circle.circlemod.core.resource.entity;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.CircleResourceLocation;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Property Registry （属性注册表）
 *
 * @author yuanxin
 * @date 2024/12/25
 */
@EventBusSubscriber(modid = CircleMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PropertyRegistry {
    /**
     * 注册属性覆盖
     *
     * @param event 事件
     */
    @SubscribeEvent
    public static void propertyOverrides(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(CircleUniRegister.ITEMS_REGISTRY.get(CircleResourceLocation.AXOLOTL_PICKAXE)
                                                                    .get(), ResourceLocation.fromNamespaceAndPath(CircleMod.MODID, "count"), (itemStack, clientWorld, livingEntity, num) -> {
                int damageValue = itemStack.getDamageValue();
                if (damageValue == 0) return 0;
                else if (damageValue == 1) return 1;
                else return 2;
            });
        });
    }
}
