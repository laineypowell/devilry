package com.laineypowell.devilry.client;

import com.laineypowell.devilry.DevilryBlocks;
import com.laineypowell.devilry.DevilryEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;

public final class DevilryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(DevilryModelLayers.IMP, ImpModel::createBodyLayer);

        EntityRendererRegistry.register(DevilryEntities.DEMON, DemonRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutoutMipped(), DevilryBlocks.DEVILISH_MALLOW,DevilryBlocks.DEVILISH_SNAPDRAGON);
    }
}
