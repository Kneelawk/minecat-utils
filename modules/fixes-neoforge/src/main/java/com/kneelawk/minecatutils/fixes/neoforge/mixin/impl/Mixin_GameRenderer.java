package com.kneelawk.minecatutils.fixes.neoforge.mixin.impl;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.renderer.GameRenderer;

// Fix #3 - Caps model-view matrix translation to prevent vanilla HUD elements from disappearing when Xaero's Minimap is installed.
@Mixin(GameRenderer.class)
public class Mixin_GameRenderer {
    @ModifyExpressionValue(method = "render",
        at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/client/ClientHooks;getGuiFarPlane()F",
            ordinal = 1))
    private float minecat_utils_fixes$fixModelViewMatrix(float original) {
        return Math.min(31000f, original);
    }
}
