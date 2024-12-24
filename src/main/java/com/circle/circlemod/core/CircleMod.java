package com.circle.circlemod.core;

import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.ResourceLocation;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

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
        CircleUniRegister.ITEMS
                .getEntries()
                .forEach(item -> {
                    LOGGER.info("注册物品：{}", ResourceLocation.findEnum(item.getKey()).name);
                });
    }
}
