package com.josinosle.magicengines.event;


import com.josinosle.magicengines.init.MagicEngines;
import com.josinosle.magicengines.init.ParticleInit;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.josinosle.magicengines.Particle.CastParticles;


@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleInit.CAST_PARTICLES.get(),CastParticles.Provider::new);
    }

}

