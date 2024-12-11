package com.circle.circlemod.core.resource;

import com.circle.circlemod.core.build.register.CircleUniRegister;
import com.circle.circlemod.core.resource.item.goldstaff.GoldStaff;

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
    }
}
