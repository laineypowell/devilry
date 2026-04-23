package com.laineypowell.devilry;

import com.laineypowell.devilry.block.SacrificialBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class DevilryBlocks {
    public static final Block SACRIFICIAL_BLOCK = new SacrificialBlock(BlockBehaviour.Properties.of());

    public static void register() {
        register("sacrificial_block", SACRIFICIAL_BLOCK);
    }

    public static void register(String name, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, Devilry.resourceLocation(name), block);
        Registry.register(BuiltInRegistries.ITEM, Devilry.resourceLocation(name), new BlockItem(block, new Item.Properties()));
    }
}
