package com.josinosle.magicengines.event;


import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.content.particle.DefenceParticles;
import com.josinosle.magicengines.init.ParticleInit;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.josinosle.magicengines.content.particle.CastParticles;


@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleInit.CAST_PARTICLES.get(),CastParticles.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.DEFENCE_PARTICLES.get(),DefenceParticles.Provider::new);
    }

}

