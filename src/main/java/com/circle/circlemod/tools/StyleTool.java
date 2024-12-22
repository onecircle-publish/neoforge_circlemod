package com.circle.circlemod.tools;

import net.minecraft.network.chat.Style;

public class StyleTool {

    /**
     * 获取默认悬停文本
     * 加粗，斜体
     *
     * @return {@link Style }
     */
    public static Style getDefaultHoverFont() {
        return Style.EMPTY.withBold(true)
                          .withItalic(true);

    }

}
