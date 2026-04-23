package com.laineypowell.devilry.mixins;

import com.laineypowell.devilry.CanSacrifice;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public final class VillagerMixin implements CanSacrifice {
    @Unique
    private static final EntityDataAccessor<Boolean> DEVILRY$SACRIFICING = SynchedEntityData.defineId(Villager.class, EntityDataSerializers.BOOLEAN);

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    public void devilry$defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DEVILRY$SACRIFICING, false);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void devilry$readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        devilry$setSacrificing(compoundTag.getBoolean("Sacrificing"));
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void devilry$addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.putBoolean("Sacrificing", devilry$isSacrificing());
    }

    @Inject(method = "customServerAiStep", at = @At("HEAD"), cancellable = true)
    public void devilry$tick(CallbackInfo ci) {
        if (devilry$isSacrificing()) {
            ci.cancel();
        }

        if (CanSacrifice.shouldTick((Villager) (Object) this)) {
            devilry$setSacrificing(true);
        }

    }

    @Override
    public boolean devilry$canSacrifice() {
        return true;
    }

    @Override
    public boolean devilry$isSacrificing() {
        return ((Villager) (Object) this).getEntityData().get(DEVILRY$SACRIFICING);
    }

    @Override
    public void devilry$setSacrificing(boolean b) {
        ((Villager) (Object) this).getEntityData().set(DEVILRY$SACRIFICING, b);
    }
}
