package com.kneelawk.minecatutils.fixes.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import net.minecraft.client.gui.LayeredDraw;

import com.kneelawk.minecatutils.fixes.MCUFConstants;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT, modid = MCUFConstants.MOD_ID)
public class MCUFNeoForgeClientEvents {
    // Fix #3 - Prevents modded HUD layers from falling off the decreased HUD space.
    @SubscribeEvent
    public static void onPostGuiLayer(RenderGuiLayerEvent.Post event) {
        // Cursed hack to increase the amount of gui layer space we have available
        event.getGuiGraphics().pose().translate(0f, 0f, -LayeredDraw.Z_SEPARATION * 3f / 4f);
    }
}
