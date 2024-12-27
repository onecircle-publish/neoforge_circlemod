package com.circle.circlemod.core.resource.item.axolotl_pickare;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.cability.AxolotlCability;
import com.circle.circlemod.core.resource.CircleResourceLocation;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


/**
 * 美西螈镐
 *
 * @author yuanxin
 * @date 2024/12/23
 */
public class AxolotlPickaxe extends PickaxeItem {
    private AxolotlCability cability = new AxolotlCability();

    public AxolotlPickaxe() {
        super(Tiers.IRON, new Item.Properties().durability(2)
                                               .attributes(PickaxeItem.createAttributes(Tiers.IRON, 1, -2.1F)));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 3;
    }

    public static void registerItemProperty() {
        ItemProperties.register(CircleUniRegister.ITEMS_REGISTRY.get(CircleResourceLocation.AXOLOTL_PICKAXE)
                                                                .get(), ResourceLocation.fromNamespaceAndPath(CircleMod.MODID, "count"), (itemStack, clientWorld, livingEntity, num) -> {
            int damageValue = itemStack.getDamageValue();
            if (damageValue == 0) return 0;
            else if (damageValue == 1) return 1;
            else return 2;
        });
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, damage);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        if (stack.getMaxDamage() - stack.getDamageValue() > 1) return 1;
        return 0;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        cability.repire(level, stack);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        cability.refreshRepairTimer();
        if (this.getMaxDamage(stack) - stack.getDamageValue() == 1) {
            if (!state.isEmpty() && !state.isAir()) {
                BlockPos above = pos.above();
                BlockPos below = pos.below();
                BlockPos east = pos.east();
                BlockPos west = pos.west();
                BlockPos north = pos.north();
                BlockPos south = pos.south();

                ArrayList<BlockPos> list = new ArrayList<>();
                list.add(above);
                list.add(below);
                list.add(east);
                list.add(west);
                list.add(north);
                list.add(south);

                list.forEach(blockPos -> {
                    BlockState blockState = level.getBlockState(blockPos);
                    if (blockState.isEmpty() || blockState.isAir()) {
                        level.setBlock(blockPos, state, 2);
                    }
                });
            }

        }
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }
}
