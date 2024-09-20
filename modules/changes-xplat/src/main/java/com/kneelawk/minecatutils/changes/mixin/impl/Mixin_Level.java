package com.kneelawk.minecatutils.changes.mixin.impl;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

@Mixin(Level.class)
public class Mixin_Level {
    @ModifyExpressionValue(
        method = "explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;ZLnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/level/Explosion;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;getDestroyType(Lnet/minecraft/world/level/GameRules$Key;)Lnet/minecraft/world/level/Explosion$BlockInteraction;",
            ordinal = 1))
    private Explosion.BlockInteraction minecat_utils_changes$onExplode(Explosion.BlockInteraction original) {
        return Explosion.BlockInteraction.KEEP;
    }
}
