package com.circle.circlemod.core.resource;

import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.item.axolotl_sword.AxolotlSword;
import com.circle.circlemod.core.resource.item.goldstaff.GoldStaff;
import com.circle.circlemod.core.resource.item.pickaxe_ultra.PickaxeUltra;

/**
 * Circle 资源
 * 从这里开始注册内容。
 *
 * @author yuanxin
 * @date 2024/12/12
 */
public class CircleResource {
    static {
        CircleUniRegister.registerItem(ResourceLocation.GOLD_STAFF, GoldStaff::new);
        CircleUniRegister.registerItem(ResourceLocation.PICKAXE_ULTRA, () -> new PickaxeUltra());
        CircleUniRegister.registerItem(ResourceLocation.AXOLOTL_SWORD, AxolotlSword::new);

    }
}
