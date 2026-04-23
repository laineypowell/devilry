package com.laineypowell.devilry;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public final class Devilry implements ModInitializer, EntityComponentInitializer, WorldComponentInitializer {
    public static final String MOD_ID = "devilry";

    public static final ComponentKey<SacrificingComponent> SACRIFICING = ComponentRegistryV3.INSTANCE.getOrCreate(resourceLocation("sacrificing"), SacrificingComponent.class);

    public static final ComponentKey<CultComponent> CULT = ComponentRegistryV3.INSTANCE.getOrCreate(resourceLocation("cult"), CultComponent.class);

    @Override
    public void onInitialize() {
        DevilryBlocks.register();
        DevilryItems.register();
        DevilryBlockEntities.register();
        DevilryEntities.register();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, resourceLocation(MOD_ID), FabricItemGroup.builder()
                        .title(Component.translatable("itemGroup.devilry"))
                        .icon(() -> DevilryBlocks.SACRIFICIAL_BLOCK.asItem().getDefaultInstance())
                        .displayItems((parameters, output) -> {
                            output.accept(DevilryBlocks.SACRIFICIAL_BLOCK);
                        })
                .build());

        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.getBlockEntity().getFluidStorage(), DevilryBlockEntities.SACRIFICIAL_BLOCK);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(Villager.class, SACRIFICING, villager -> new SacrificingComponent());
    }

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(CULT, level -> new CultComponent());
    }

    public static ResourceLocation resourceLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
