package com.laineypowell.devilry;

import com.laineypowell.devilry.block.SacrificialBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public final class SacrificingComponent implements AutoSyncedComponent {
    public boolean sacrificing;

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        sacrificing = tag.getBoolean("Sacrificing");
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        tag.putBoolean("Sacrificing", sacrificing);
    }

    public boolean shouldSacrifice(Entity entity) {
        if (entity.isPassenger()) {
            return false;
        }

        var level = entity.level();

        var blockPos = entity.getOnPos();
        if (level.isLoaded(blockPos)) {
            var chunk = level.getChunk(blockPos);

            var blockEntities = chunk.getBlockEntitiesPos();
            if (!blockEntities.isEmpty()) {
                for (var blockPos0 : blockEntities) {
                    if (blockPos0.distManhattan(blockPos) <= 3) {
                        var blockState = level.getBlockState(blockPos0);

                        if (blockState.is(DevilryBlocks.SACRIFICIAL_BLOCK) && !blockState.getValue(BlockStateProperties.OCCUPIED) && blockState.getValue(SacrificialBlock.SIDE) == SacrificialBlock.Side.FRONT) {
                            SacrificialBlock.setProperty(level, blockPos0, BlockStateProperties.OCCUPIED, true);

                            var up = Vec3.atLowerCornerOf(blockPos0.above());
                            entity.moveTo(up.x + 0.5f, up.y, up.z + 0.5f);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
