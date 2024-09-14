package com.kneelawk.minecatutils.fixes.mixin.impl;

import java.util.Set;

import com.google.common.collect.Sets;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.PlayerAdvancements;

@Mixin(SimpleCriterionTrigger.class)
public class Mixin_SimpleCriterionTrigger {
    @Inject(method = "lambda$addPlayerListener$0", at = @At("RETURN"), cancellable = true)
    private static void minecat_utils_fixes$onCreate(PlayerAdvancements advancements,
                                                     CallbackInfoReturnable<Set<CriterionTrigger.Listener<?>>> cir) {
        cir.setReturnValue(Sets.newLinkedHashSet());
    }
}
