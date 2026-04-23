package com.laineypowell.devilry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface CanSacrifice {

    boolean devilry$canSacrifice();

    boolean devilry$isSacrificing();

    void devilry$setSacrificing(boolean b);

    static boolean shouldTick(Entity entity) {
        var level = entity.level();

        var blockPos = entity.getOnPos();
        if (level.isLoaded(blockPos)) {
            var chunk = level.getChunk(blockPos);

            var blockEntities = chunk.getBlockEntitiesPos();
            if (!blockEntities.isEmpty()) {
                return blockEntities.stream().anyMatch(blockPos0 -> {
                    var blockState = level.getBlockState(blockPos0);
                    return blockState.is(DevilryBlocks.SACRIFICIAL_BLOCK) && !blockState.getValue(BlockStateProperties.OCCUPIED);
                });
            }
        }

        return false;
    }
}
