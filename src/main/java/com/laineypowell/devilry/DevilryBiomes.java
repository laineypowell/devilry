package com.laineypowell.devilry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public final class DevilryBiomes {
    public static final ResourceKey<Biome> DEVILISH = resourceKey("devilish");

    public static ResourceKey<Biome> resourceKey(String name) {
        return ResourceKey.create(Registries.BIOME, Devilry.resourceLocation(name));
    }
}
