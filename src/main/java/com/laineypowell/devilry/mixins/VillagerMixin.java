package com.laineypowell.devilry.mixins;

import com.laineypowell.devilry.CanSacrifice;
import com.laineypowell.devilry.Devilry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public final class VillagerMixin implements CanSacrifice {

    @Inject(method = "customServerAiStep", at = @At("HEAD"), cancellable = true)
    public void devilry$customServerAiStep(CallbackInfo ci) {
        var villager = (Villager) (Object) this;
        var sacrificing = villager.getComponent(Devilry.SACRIFICING);
        if (sacrificing.sacrificing) {
            devilry$stop();

            ci.cancel();
            return;
        }

        if (devilry$canSacrifice() && sacrificing.shouldSacrifice((Villager) (Object) this)) {
            sacrificing.sacrificing = true;
            villager.syncComponent(Devilry.SACRIFICING);

            devilry$stop();
            ci.cancel();
        }
    }

    @Unique
    public void devilry$stop() {
        var villager = (Villager) (Object) this;

        villager.getBrain().stopAll((ServerLevel) villager.level(), villager);
        villager.getNavigation().stop();
    }

    @Override
    public boolean devilry$canSacrifice() {
        return !((Villager) (Object) this).isBaby();
    }
}
