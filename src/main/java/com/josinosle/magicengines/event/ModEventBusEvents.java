package com.josinosle.magicengines.event;


import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.particle.DefenseParticles;
import com.josinosle.magicengines.particle.StinkyParticles;
import com.josinosle.magicengines.registry.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.josinosle.magicengines.particle.CastParticles;


@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.CAST_PARTICLES.get(),CastParticles.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.DEFENSE_PARTICLES.get(), DefenseParticles.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.STINKY_PARTICLES.get(), StinkyParticles.Provider::new);
    }

}

