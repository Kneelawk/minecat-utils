package com.kneelawk.minecatutils.fixes.mixin.impl;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;

import com.kneelawk.minecatutils.fixes.BlockingConcurrentIterationArrayList;

@Mixin(ReloadableResourceManager.class)
public class Mixin_ReloadableResourceManager {
    @Mutable
    @Shadow
    @Final
    private List<PreparableReloadListener> listeners;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void minecat_utils_fixes$onCreate(PackType type, CallbackInfo ci) {
        listeners = new BlockingConcurrentIterationArrayList<>(true);
    }
}
