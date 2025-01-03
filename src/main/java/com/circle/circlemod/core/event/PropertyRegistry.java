package com.circle.circlemod.core.event;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.resource.item.axolotl_pickare.AxolotlPickaxe;
import com.circle.circlemod.core.resource.item.axolotl_sword.AxolotlSword;
import com.circle.circlemod.core.resource.item.chest_shield.ChestShield;
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
            AxolotlPickaxe.registerItemProperty();
            AxolotlSword.registerItemProperty();

            ChestShield.registerItemProperty();
        });
    }
}
