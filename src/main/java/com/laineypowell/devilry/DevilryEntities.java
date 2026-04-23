package com.laineypowell.devilry;

import com.laineypowell.devilry.entity.Demon;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class DevilryEntities {
    public static final EntityType<Demon> DEMON = EntityType.Builder.of(Demon::new, MobCategory.MISC).sized(1.0f, 2.0f).build();

    public static void register() {
        register("demon", DEMON);
        FabricDefaultAttributeRegistry.register(DEMON, Demon.createMobAttributes());
    }

    public static void register(String name, EntityType<?> type) {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, Devilry.resourceLocation(name), type);
    }
}
