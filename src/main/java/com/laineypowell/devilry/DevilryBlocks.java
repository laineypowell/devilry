package com.laineypowell.devilry;

import com.laineypowell.devilry.block.DevilishFlowerBlock;
import com.laineypowell.devilry.block.SacrificialBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public final class DevilryBlocks {
    public static final Block SACRIFICIAL_BLOCK = new SacrificialBlock(Properties.of());
    public static final Block DEVILISH_DIRT = new Block(Properties.of().sound(SoundType.GRAVEL));
    public static final Block DEVILISH_MALLOW = new DevilishFlowerBlock(Properties.of().sound(SoundType.GRASS).noOcclusion().noCollission().instabreak());
    public static final Block DEVILISH_SNAPDRAGON = new DevilishFlowerBlock(Properties.ofFullCopy(DEVILISH_MALLOW));

    public static void register() {
        register("sacrificial_block", SACRIFICIAL_BLOCK);
        register("devilish_dirt", DEVILISH_DIRT);
        register("devilish_mallow", DEVILISH_MALLOW);
        register("devilish_snapdragon", DEVILISH_SNAPDRAGON);
    }

    public static void register(String name, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, Devilry.resourceLocation(name), block);
        Registry.register(BuiltInRegistries.ITEM, Devilry.resourceLocation(name), new BlockItem(block, new Item.Properties()));
    }
}
