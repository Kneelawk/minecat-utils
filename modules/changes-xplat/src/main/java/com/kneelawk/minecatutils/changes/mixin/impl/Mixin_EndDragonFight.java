package com.kneelawk.minecatutils.changes.mixin.impl;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.level.dimension.end.EndDragonFight;

@Mixin(EndDragonFight.class)
public class Mixin_EndDragonFight {
    @ModifyExpressionValue(method = "setDragonKilled", at = @At(value = "FIELD",
        target = "Lnet/minecraft/world/level/dimension/end/EndDragonFight;previouslyKilled:Z", ordinal = 0))
    private boolean minecat_utils_changes$onDragonKilled(boolean previouslyKilled) {
        return false;
    }
}
