package com.laineypowell.devilry.block;

import com.laineypowell.devilry.DevilryBlockEntities;
import com.laineypowell.devilry.DevilryBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public final class SacrificialBlock extends Block implements EntityBlock {
    public static final EnumProperty<Side> SIDE = EnumProperty.create("side", Side.class);

    public static final BooleanProperty FILLED = BooleanProperty.create("filled");

    public SacrificialBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any()
                .setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X)
                .setValue(BlockStateProperties.OCCUPIED, false)
                .setValue(SIDE, Side.FRONT)
                .setValue(FILLED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_AXIS);
        builder.add(BlockStateProperties.OCCUPIED);
        builder.add(SIDE);
        builder.add(FILLED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        var axis = horizontalAxis(blockPlaceContext.getHorizontalDirection().getAxis(), blockPlaceContext.getClickedFace().getAxis());

        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, axis);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        var side = blockState.getValue(SIDE);
        level.setBlock(getRelative(blockPos, blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS), side), blockState.setValue(SIDE, side.getOpposite()), Block.UPDATE_ALL);
    }

    public Direction.Axis horizontalAxis(Direction.Axis facing, Direction.Axis clicked) {
        return clicked.isVertical() ? facing : clicked.ordinal() >= facing.ordinal() ? clicked : facing;
    }

    @Override
    protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        var axis = blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS);
        var side = blockState.getValue(SIDE);

        var front = levelAccessor.getBlockState(getRelative(blockPos, axis, side));
        if (!front.is(this) || front.getValue(BlockStateProperties.HORIZONTAL_AXIS) != axis || front.getValue(SIDE) != side.getOpposite()) {
            return Blocks.AIR.defaultBlockState();
        }
        return blockState;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return DevilryBlockEntities.SACRIFICIAL_BLOCK.create(blockPos, blockState);
    }

    public static <T extends Comparable<T>> void setProperty(Level level, BlockPos blockPos, Property<T> property, T t) {
        var blockState = level.getBlockState(blockPos);

        if (blockState.is(DevilryBlocks.SACRIFICIAL_BLOCK)) {

            level.setBlock(blockPos, blockState.setValue(property, t), Block.UPDATE_ALL);
        }
    }

    public static BlockPos getRelative(BlockPos blockPos, Direction.Axis axis, Side side) {
        return blockPos.relative(axis, side == Side.FRONT ? -1 : 1);
    }

    public enum Side implements StringRepresentable {
        FRONT("front"),
        BACK("back");

        private final String name;

        Side(String name) {
            this.name = name;
        }

        public Side getOpposite() {
            return this == FRONT ? BACK : FRONT;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
