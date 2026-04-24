package com.laineypowell.devilry.mixins;

import com.laineypowell.devilry.DevilryBiomes;
import com.laineypowell.devilry.DevilryBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public final class ServerLevelMixin {

    @Inject(method = "tickChunk", at = @At("HEAD"))
    public void devilry$tickChunk(LevelChunk levelChunk, int i, CallbackInfo ci) {
        if (i > 0) {
            var chunkPos = levelChunk.getPos();

            var level = levelChunk.getLevel();
            var random = level.random;

            var blockPos = new BlockPos.MutableBlockPos();

            var sections = levelChunk.getSections();
            for (var j = levelChunk.getMinSection(); j < levelChunk.getMaxSection(); j++) {
                var section = sections[levelChunk.getSectionIndexFromSectionY(j)];
                if (section.maybeHas(this::devilry$canTick) && random.nextInt(15) == 0) {
                    for (var k = 0; k < 16 * 16 * 16; k++) {
                        var x = k % 16;
                        var y = (k / 16) % 16;
                        var z = k / (16 * 16);

                        if (random.nextInt(2500) == 0) {
                            var blockState = levelChunk.getBlockState(blockPos.set(x + (chunkPos.x << 4), y + (j << 4), z + (chunkPos.z << 4)));

                            if (devilry$canBurn(blockState)) {
                                devilry$burnBlock(level, blockPos, random);
                            }

                            if (level.getBiome(blockPos).is(DevilryBiomes.DEVILISH)) {
                                if (blockState.is(BlockTags.DIRT)) {
                                    level.setBlock(blockPos, DevilryBlocks.DEVILISH_DIRT.defaultBlockState(), Block.UPDATE_ALL);
                                }

                                if (blockState.is(Blocks.DANDELION)) {
                                    level.setBlock(blockPos, DevilryBlocks.DEVILISH_MALLOW.defaultBlockState(), Block.UPDATE_ALL);
                                } else if (blockState.is(BlockTags.SMALL_FLOWERS)) {
                                    level.setBlock(blockPos, DevilryBlocks.DEVILISH_SNAPDRAGON.defaultBlockState(), Block.UPDATE_ALL);
                                }
                            } else if (blockState.is(DevilryBlocks.DEVILISH_DIRT)) {
                                level.setBlock(blockPos, Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);
                            }
                        }
                    }
                }
            }
        }
    }

    @Unique
    public void devilry$burnBlock(Level level, BlockPos blockPos, RandomSource random) {
        for (var direction : Direction.values()) {
            var blockState = Blocks.FIRE.defaultBlockState();

            var relative = blockPos.relative(direction);
            if (level.getBiome(relative).is(DevilryBiomes.DEVILISH) && blockState.canSurvive(level, relative) && level.getBlockState(relative).canBeReplaced() && random.nextInt(15) == 0) {
                level.setBlock(relative, blockState, Block.UPDATE_ALL);
            }
        }
    }

    @Unique
    public boolean devilry$canBurn(BlockState blockState) {
        return blockState.is(BlockTags.LEAVES) ||
                blockState.is(BlockTags.LOGS_THAT_BURN) ||
                blockState.is(BlockTags.PLANKS) ||
                blockState.is(BlockTags.WOODEN_STAIRS) ||
                blockState.is(BlockTags.WOODEN_SLABS) ||
                blockState.is(BlockTags.WOODEN_FENCES) ||
                blockState.is(BlockTags.WOOL);
    }

    @Unique
    public boolean devilry$canBecomeDevilish(BlockState blockState) {
        return blockState.is(BlockTags.DIRT) || blockState.is(BlockTags.SMALL_FLOWERS);
    }

    @Unique
    public boolean devilry$canTick(BlockState blockState) {
        return devilry$canBurn(blockState) || devilry$canBecomeDevilish(blockState);
    }

}
