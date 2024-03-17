package com.josinosle.magicengines.event;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.gui.overlay.ManaBarOverlay;
import com.josinosle.magicengines.util.KeyboardHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID, value = Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ClientModBusEvents {


    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event){
        event.register(KeyboardHelper.CAST_INAIR_KEY);
        event.register(KeyboardHelper.CASTCALC_KEY);
    }

    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiOverlaysEvent event){
        event.registerBelow(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "mana_overlay", ManaBarOverlay::renderManaBar);
    }

}
