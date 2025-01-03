package com.circle.circlemod.core;

import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.CircleResourceLocation;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.model.obj.ObjLoader;
import org.slf4j.Logger;

import static com.circle.circlemod.core.builds.register.CircleUniRegister.CREATIVE_MODE_TABS;

/**
 * Circle Mod
 *
 * @author yuanxin
 * @date 2024/12/12
 */
@Mod(CircleMod.MODID)
public class CircleMod {
    public static final String MODID = "circle";
    public static final Logger LOGGER = LogUtils.getLogger();
    public CircleUniRegister circleUniRegister = null;

    /**
     * Circle Mod
     */
    public CircleMod(IEventBus bus, ModContainer modContainer) {
        bus.addListener(this::commonSetup);
        circleUniRegister = new CircleUniRegister(bus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CircleUniRegister.ITEMS.getEntries()
                               .forEach(item -> {
                                   LOGGER.info("注册物品：{}", CircleResourceLocation.findEnum(item.getKey()).name);
                               });


    }
}
