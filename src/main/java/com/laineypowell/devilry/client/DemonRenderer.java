package com.laineypowell.devilry.client;

import com.laineypowell.devilry.Devilry;
import com.laineypowell.devilry.entity.Demon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class DemonRenderer extends MobRenderer<Demon, EntityModel<Demon>> {
    public static final ResourceLocation TEXTURE = Devilry.resourceLocation("textures/entity/imp.png");

    public DemonRenderer(EntityRendererProvider.Context context) {
        super(context, new ImpModel(context.bakeLayer(DevilryModelLayers.IMP)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(Demon entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(Demon livingEntity, PoseStack poseStack, float f) {
        var s = 15.0f / 16.0f;
        poseStack.scale(s, s, s);
    }
}
