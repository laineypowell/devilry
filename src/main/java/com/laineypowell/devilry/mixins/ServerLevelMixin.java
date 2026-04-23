package com.laineypowell.devilry.mixins;

import com.laineypowell.devilry.Devilry;
import com.laineypowell.devilry.DevilryBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
                if (section.maybeHas(Devilry::blockStateCanBurn) && random.nextInt(15) == 0) {
                    for (var k = 0; k < 16 * 16 * 16; k++) {
                        var x = k % 16;
                        var y = (k / 16) % 16;
                        var z = k / (16 * 16);
                        if (Devilry.blockStateCanBurn(levelChunk.getBlockState(blockPos.set(x + (chunkPos.x << 4), y + (j << 4), z + (chunkPos.z << 4)))) && random.nextInt(2500) == 0) {
                            devilry$tickBlock(level, blockPos, random);
                        }
                    }
                }
            }
        }
    }

    @Unique
    public void devilry$tickBlock(Level level, BlockPos blockPos, RandomSource random) {
        for (var direction : Direction.values()) {
            var blockState = Blocks.FIRE.defaultBlockState();

            var relative = blockPos.relative(direction);
            if (level.getBiome(relative).is(DevilryBiomes.DEVILISH) && blockState.canSurvive(level, relative) && level.getBlockState(relative).canBeReplaced() && random.nextInt(15) == 0) {
                level.setBlock(relative, blockState, Block.UPDATE_ALL);
            }
        }
    }

}
