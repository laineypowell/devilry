package com.laineypowell.devilry;

import com.laineypowell.devilry.block.SacrificialBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class DevilryBlockEntities {
    public static final BlockEntityType<SacrificialBlockEntity> SACRIFICIAL_BLOCK = BlockEntityType.Builder.of(SacrificialBlockEntity::new, DevilryBlocks.SACRIFICIAL_BLOCK).build();

    public static void register() {
        register("sacrificial_block", SACRIFICIAL_BLOCK);
    }

    public static void register(String name, BlockEntityType<?> type) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Devilry.resourceLocation(name), type);
    }
}
