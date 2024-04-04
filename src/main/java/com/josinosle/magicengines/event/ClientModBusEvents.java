package com.josinosle.magicengines.event;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.entity.models.AbstractSpellModel;
import com.josinosle.magicengines.entity.models.ModModelLayers;
import com.josinosle.magicengines.entity.spells.beamrenderers.AbstractSpellBeamRenderer;
import com.josinosle.magicengines.entity.spells.projectilerenderers.AbstractSpellProjectileRenderer;
import com.josinosle.magicengines.gui.overlay.ManaBarOverlay;
import com.josinosle.magicengines.registry.EntityRegistry;
import com.josinosle.magicengines.util.KeyboardHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
        event.register(KeyboardHelper.CASTCANTRIP_KEY);
    }

    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiOverlaysEvent event){
        event.registerBelow(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "mana_overlay", ManaBarOverlay::renderManaBar);
    }

    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.ABSTRACT_SPELL.get(), AbstractSpellProjectileRenderer::new);
        event.registerEntityRenderer(EntityRegistry.ABSTRACT_SPELL.get(), AbstractSpellBeamRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.ABSTRACT_SPELL_LAYER, AbstractSpellModel::createBodyLayer);
    }

}
