package com.circle.circlemod.core.resource;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * 资源位置
 *
 * @author yuanxin
 * @date 2024/12/12
 */
public enum ResourceLocation {
    GOLD_STAFF("gold_staff", "金法杖"),
    PICKAXE_ULTRA("pickaxe_ultra", "超级稿", new HashMap<>() {
        {
            put(TranslateKey.HOVER.key, "一个挖掘神器");
        }
    });

    public final String key;
    public final String name;
    public HashMap<String, String> translateMap = new HashMap<>();

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
     * 资源位置
     *
     * @param key  钥匙
     * @param name 名字
     */
    ResourceLocation(String key, String name, @Nullable HashMap<String, String> translateMap) {
        this.key = key;
        this.name = name;
        this.translateMap = translateMap;
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


    /**
     * 获取拼接后的 translate 键值列表
     * <p>
     * ResourceKey<Item> item.xxx.xxx
     *
     * @return {@link HashMap }<{@link String }, {@link String }>
     */
    public HashMap<String, String> getTranslateKeyValueList(String key) {
        HashMap<String, String> targetTransMap = new HashMap<>();

        translateMap.entrySet()
                    .forEach(translate -> {
                        String path = key + "." + translate.getKey();
                        String content = translate.getValue();
                        targetTransMap.put(path, content);
                    });
        return targetTransMap;
    }

    public String getName(TranslateKey translateKey) {
        return this.translateMap.get(translateKey);
    }

    public enum TranslateKey {
        HOVER("hover");

        public String key;

        TranslateKey(String key) {
            this.key = key;
        }
    }
}