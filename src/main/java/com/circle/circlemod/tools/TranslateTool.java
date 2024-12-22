package com.circle.circlemod.tools;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.resource.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class TranslateTool {
    /**
     * 添加物品文字翻译
     *
     * @param components   组件
     * @param location     位置
     * @param TranslateKey translate 键
     * @param style        风格
     */
    public static void addItemTranslate(List<Component> components, ResourceLocation location, ResourceLocation.TranslateKey TranslateKey, Style style) {
        components.add(Component.translatable("item" + "." + CircleMod.MODID + "." + location.key + "." + TranslateKey.key)
                                .withStyle(style));
    }
}
