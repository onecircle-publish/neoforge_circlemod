package com.circle.circlemod.core.resource;

import net.minecraft.resources.ResourceKey;

/**
 * 资源位置
 *
 * @author yuanxin
 * @date 2024/12/12
 */
public enum ResourceLocation {
    GOLD_STAFF("gold_staff", "金法杖");

    public final String key;
    public final String name;

    /**
     * 资源位置
     *
     * @param key  钥匙
     * @param name 名字
     */
    ResourceLocation(String key, String name) {
        this.key = key;
        this.name = name;
    }


    /**
     * 查找枚举
     *
     * @param resourceKey 资源键
     * @return {@link ResourceLocation }
     */
    public static ResourceLocation findEnum(ResourceKey resourceKey) {
        return findEnum(resourceKey
                .location()
                .getPath());
    }

    /**
     * 查找枚举
     *
     * @param key 钥匙
     * @return {@link ResourceLocation }
     */
    public static ResourceLocation findEnum(String key) {
        for (ResourceLocation resource : ResourceLocation.values()) {
            if (resource.key.equals(key)) {
                return resource;
            }
        }
        throw new IllegalArgumentException("key is invalid");
    }
}
