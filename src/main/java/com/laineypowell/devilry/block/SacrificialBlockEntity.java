package com.laineypowell.devilry.block;

import com.google.common.base.Suppliers;
import com.laineypowell.devilry.DevilryBlockEntities;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Supplier;

public final class SacrificialBlockEntity extends BlockEntity {
    private final SingleFluidStorage fluidStorage = new SingleFluidStorage() {
        @Override
        protected long getCapacity(FluidVariant variant) {
            return FluidConstants.BLOCK;
        }

        @Override
        protected void onFinalCommit() {
            if (level != null) {
                var blockPos = getBlockPos();
                SacrificialBlock.setProperty(level, blockPos, SacrificialBlock.FILLED, isEmpty());
                level.blockEntityChanged(blockPos);
            }
        }

        @Override
        protected boolean canInsert(FluidVariant variant) {
            return false;
        }
    };

    private final Supplier<SacrificialBlockEntity> blockEntity = Suppliers.memoize(() -> {
        var blockState = getBlockState();

        var axis = blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS);
        var side = blockState.getValue(SacrificialBlock.SIDE);
        return side == SacrificialBlock.Side.FRONT ? this : (SacrificialBlockEntity) level.getBlockEntity(SacrificialBlock.getRelative(getBlockPos(), axis, side));
    });

    public SacrificialBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(DevilryBlockEntities.SACRIFICIAL_BLOCK, blockPos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        fluidStorage.readNbt(compoundTag, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        fluidStorage.writeNbt(compoundTag, provider);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        var tag = new CompoundTag();
        saveAdditional(tag, provider);
        return tag;
    }

    public boolean isEmpty() {
        return fluidStorage.isResourceBlank() || fluidStorage.amount <= 0;
    }

    public SingleFluidStorage getFluidStorage() {
        return fluidStorage;
    }

    public SacrificialBlockEntity getBlockEntity() {
        return blockEntity.get();
    }
}
