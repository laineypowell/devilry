package com.laineypowell.devilry.client;

import com.laineypowell.devilry.DevilryEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public final class DevilryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(DevilryModelLayers.IMP, ImpModel::createBodyLayer);

        EntityRendererRegistry.register(DevilryEntities.DEMON, DemonRenderer::new);
    }
}
