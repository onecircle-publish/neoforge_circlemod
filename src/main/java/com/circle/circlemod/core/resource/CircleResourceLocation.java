package com.circle.circlemod.core.resource;

import com.circle.circlemod.core.CircleMod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * 资源位置
 *
 * @author yuanxin
 * @date 2024/12/12
 */
public enum CircleResourceLocation {
    GOLD_STAFF("gold_staff", "金法杖"),
    PICKAXE_ULTRA("pickaxe_ultra", "超级稿", new HashMap<>() {
        {
            put(TranslateKey.HOVER.key, "一个挖掘神器");
        }
    }),
    AXOLOTL_PICKAXE("axolotl_pickaxe", "美西螈镐"),
    AXOLOTL_SWORD("axolotl_sword", "美西螈剑"),
    AXOLOTL_BOW("axolotl_bow", "美西螈弓"),
    CHEST_SHIELD("chest_shield", "存钱罐盾"),

    //  ---- 实体
    AXOLOTL_ARROW("axolotl_arrow", "美西螈箭");

    public final String key;
    public final String name;
    public HashMap<String, String> translateMap = new HashMap<>();

    /**
     * 资源位置
     *
     * @param key  钥匙
     * @param name 名字
     */
    CircleResourceLocation(String key, String name) {
        this.key = key;
        this.name = name;

    }

    /**
     * 资源位置
     *
     * @param key  钥匙
     * @param name 名字
     */
    CircleResourceLocation(String key, String name, @Nullable HashMap<String, String> translateMap) {
        this.key = key;
        this.name = name;
        this.translateMap = translateMap;
    }

    /**
     * 获取MC内置资源位置
     *
     * @return {@link ResourceLocation }
     */
    public ResourceLocation getResourceLocation() {
        return ResourceLocation.fromNamespaceAndPath(CircleMod.MODID, this.key);
    }

    /**
     * 查找枚举
     *
     * @param resourceKey 资源键
     * @return {@link CircleResourceLocation }
     */
    public static CircleResourceLocation findEnum(ResourceKey resourceKey) {
        return findEnum(resourceKey
                .location()
                .getPath());
    }

    /**
     * 查找枚举
     *
     * @param key 钥匙
     * @return {@link CircleResourceLocation }
     */
    public static CircleResourceLocation findEnum(String key) {
        for (CircleResourceLocation resource : CircleResourceLocation.values()) {
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