## CircleMod开发记录

### 注册入口
找到`com.circle.circlemod.resource.CircleResource`，在static块中定义注册内容。

例如注册一个Item物品
```Java
public class CircleResource {
    static {
        CircleUniRegister.registerItem(ResourceLocation.GOLD_STAFF, GoldStaff::new);
    }
}
```
这是neoforge的一个Deferred注册的封装，第一个参数是物品id，第二个参数是一个`Supplier`

GoldStaff就是该物品的实现类，如果没有任何特别的需求，第二个参数可以替换成`() -> new Item.Properties()`。