package com.circle.circlemod.core.resource.entity.axolotl_arrow;

import com.circle.circlemod.core.CircleMod;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class AxolotlArrowRenderer extends ArrowRenderer {
    public static final ResourceLocation SPECTRAL_ARROW_LOCATION = ResourceLocation.fromNamespaceAndPath(CircleMod.MODID, "textures/entity/projectiles/axolotl_arrow.png");

    public AxolotlArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity entity) {
        return SPECTRAL_ARROW_LOCATION;
    }
}
