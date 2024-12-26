package com.circle.circlemod.core.resource;

import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.entity.axolotl_arrow.AxolotlArrow;
import com.circle.circlemod.core.resource.entity.axolotl_arrow.AxolotlArrowRenderer;
import com.circle.circlemod.core.resource.item.axolotl_bow.AxolotlBow;
import com.circle.circlemod.core.resource.item.axolotl_pickare.AxolotlPickaxe;
import com.circle.circlemod.core.resource.item.axolotl_sword.AxolotlSword;
import com.circle.circlemod.core.resource.item.goldstaff.GoldStaff;
import com.circle.circlemod.core.resource.item.pickaxe_ultra.PickaxeUltra;
import net.minecraft.world.entity.MobCategory;


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
        CircleUniRegister.registerItem(CircleResourceLocation.AXOLOTL_SWORD, AxolotlSword::new);
        CircleUniRegister.registerItem(CircleResourceLocation.AXOLOTL_BOW, AxolotlBow::new);

        CircleUniRegister.<AxolotlArrow>registerEntity(CircleResourceLocation.AXOLOTL_ARROW, AxolotlArrow::new, AxolotlArrowRenderer::new, MobCategory.CREATURE);
    }
}
