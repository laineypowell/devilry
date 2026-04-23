package com.laineypowell.devilry;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class Devilry implements ModInitializer {
    public static final String MOD_ID = "devilry";

    @Override
    public void onInitialize() {
        DevilryBlocks.register();
        DevilryItems.register();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, resourceLocation(MOD_ID), FabricItemGroup.builder()
                        .title(Component.translatable("itemGroup.devilry"))
                        .icon(() -> DevilryBlocks.SACRIFICIAL_BLOCK.asItem().getDefaultInstance())
                        .displayItems((parameters, output) -> {
                            output.accept(DevilryBlocks.SACRIFICIAL_BLOCK);
                        })
                .build());
    }

    public static ResourceLocation resourceLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
