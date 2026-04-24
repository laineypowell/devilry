package com.laineypowell.devilry.block;

import com.laineypowell.devilry.DevilryBiomes;
import com.laineypowell.devilry.DevilryBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public final class DevilishFlowerBlock extends BushBlock {
    public static final MapCodec<DevilishFlowerBlock> CODEC = simpleCodec(DevilishFlowerBlock::new);

    public static final BooleanProperty EXTINGUISHED = BooleanProperty.create("extinguished");

    public DevilishFlowerBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(EXTINGUISHED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(EXTINGUISHED);
    }

    @Override
    protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        return getBlockState(blockState, levelAccessor, blockPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return getBlockState(defaultBlockState(), blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos());
    }

    public BlockState getBlockState(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos) {
        return blockState.setValue(EXTINGUISHED, !levelAccessor.getBiome(blockPos).is(DevilryBiomes.DEVILISH));
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.is(BlockTags.DIRT) || blockState.is(DevilryBlocks.DEVILISH_DIRT);
    }

    @Override
    protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!blockState.getValue(EXTINGUISHED) && !entity.fireImmune() && !entity.isOnFire() && entity instanceof LivingEntity livingEntity) {
            livingEntity.setRemainingFireTicks(20 * 4);
        }
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}
