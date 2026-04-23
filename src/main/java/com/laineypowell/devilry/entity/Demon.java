package com.laineypowell.devilry.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;

import java.util.List;

public final class Demon extends PathfinderMob {
    public static final EntityDataAccessor<Integer> DEMON_TYPE = SynchedEntityData.defineId(Demon.class, EntityDataSerializers.INT);

    private final List<EntityDimensions> sizes = List.of(
            EntityDimensions.scalable(1.0f, 2.0f)
    );

    public Demon(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder.define(DEMON_TYPE, 0));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        entityData.set(DEMON_TYPE, compoundTag.getInt("DemonType"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("DemonType", entityData.get(DEMON_TYPE));
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return sizes.get(entityData.get(DEMON_TYPE));
    }
}
