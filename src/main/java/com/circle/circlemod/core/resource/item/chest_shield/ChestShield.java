package com.circle.circlemod.core.resource.item.chest_shield;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.CircleResourceLocation;
import mod.azure.azurelib.sblforked.util.RandomUtil;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class ChestShield extends ShieldItem {
    static final int MAX_DAMAGE = 32;

    public ChestShield() {
        super(new Item.Properties().durability(MAX_DAMAGE)
                                   .component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)
                                   .component(DataComponents.CONTAINER, ItemContainerContents.fromItems(new ArrayList<ItemStack>())));

    }

    public static void registerItemProperty() {
        ItemProperties.register(CircleUniRegister.ITEMS_REGISTRY.get(CircleResourceLocation.CHEST_SHIELD)
                                                                .get(), ResourceLocation.fromNamespaceAndPath(CircleMod.MODID, "blocking"), (itemStack, clientWorld, livingEntity, num) -> {


            if (livingEntity != null && itemStack.getItem() instanceof ChestShield) {
                return livingEntity.isUsingItem() ? 1 : 0;
            }
            return 0;
        });
    }


    @Override
    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        super.onDestroyed(itemEntity, damageSource);
        ItemStack item = itemEntity.getItem();
        ItemContainerContents itemContainerContents = item.getComponents()
                                                          .get(DataComponents.CONTAINER);
        itemContainerContents.stream()
                             .forEach(content -> {
                                 CircleMod.LOGGER.debug("【存储箱内容】: {}", content.getDisplayName());
                             });

    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        Level level = entity.level();
        if (stack.getMaxDamage() - stack.getDamageValue() <= 1) {
            doChestShieldBroken(stack, level, entity);
        }

        return super.damageItem(stack, 1, entity, onBroken);
    }


    public Arrow createBrokenArrow(Level level) {
        Arrow arrow = EntityType.ARROW.create(level);

        return arrow;
    }

    /**
     * 盾损坏的时候，发射出所有栈内物品
     *
     * @param stack  叠
     * @param level  水平
     * @param entity 实体
     */
    public void doChestShieldBroken(ItemStack stack, Level level, LivingEntity entity) {
        ItemContainerContents itemContainerContents = stack.getComponents()
                                                           .get(DataComponents.CONTAINER);
        List<ItemStack> list = itemContainerContents.stream()
                                                    .toList();

        list.forEach((itemStack) -> {
            if (itemStack.is(Items.ARROW)) {
                Arrow arrow = createBrokenArrow(level);
                Vec3 position = entity.getPosition(1);
                arrow.setPos(position);

                Vec3 defaultShootVec3 = new Vec3(RandomUtil.randomNumberBetween(-1, 1), 1, RandomUtil.randomNumberBetween(-1, 1));
                int inaccuracy = 10;

                arrow.shoot(defaultShootVec3.x, defaultShootVec3.y, defaultShootVec3.z, 1, inaccuracy);
                level.addFreshEntity(arrow);
            }
        });
    }
}
