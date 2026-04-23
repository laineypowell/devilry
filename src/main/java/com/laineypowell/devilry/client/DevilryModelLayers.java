package com.laineypowell.devilry.client;

import com.laineypowell.devilry.Devilry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public final class DevilryModelLayers {
    public static final ModelLayerLocation IMP = modelLayerLocation("imp");

    public static ModelLayerLocation modelLayerLocation(String name) {
        return new ModelLayerLocation(Devilry.resourceLocation(name), name);
    }
}
