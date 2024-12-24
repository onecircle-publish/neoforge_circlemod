package com.circle.circlemod.core.resource;

import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.item.axolotl_pickare.AxolotlPickaxe;
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
        CircleUniRegister.registerItem(CircleResourceLocation.GOLD_STAFF, GoldStaff::new);
        CircleUniRegister.registerItem(CircleResourceLocation.PICKAXE_ULTRA, () -> new PickaxeUltra());
        CircleUniRegister.registerItem(CircleResourceLocation.AXOLOTL_PICKAXE, AxolotlPickaxe::new);

    }
}
