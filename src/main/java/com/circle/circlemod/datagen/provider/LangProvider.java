package com.circle.circlemod.datagen.provider;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.ResourceLocation;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;

public class LangProvider extends LanguageProvider {

    public LangProvider(PackOutput output) {
        super(output, CircleMod.MODID, "zh_cn");
    }

    public void addMapTranslate(Map<String, String> map) {
        map.entrySet()
           .forEach(data -> {
               this.add(data.getKey(), data.getValue());
           });
    }

    @Override
    protected void addTranslations() {
        // 生成物品注册的名字翻译
        CircleUniRegister.ITEMS.getEntries()
                               .forEach(itemDeferredHolder -> {
                                   ResourceKey<Item> key = itemDeferredHolder.getKey();
                                   ResourceLocation enumData = ResourceLocation.findEnum(key);

                                   // 生成物品翻译
                                   String name = enumData.name;
                                   this.add(itemDeferredHolder.get(), name);
                                   // 生成文字翻译
                                   String descriptionId = itemDeferredHolder.get()
                                                                            .getDescriptionId();
                                   HashMap<String, String> translateKeyValueList = enumData.getTranslateKeyValueList(descriptionId);
                                   addMapTranslate(translateKeyValueList);
                               });
    }
}
