package com.josinosle.magicengines.registry;

import com.josinosle.magicengines.MagicEngines;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MagicEngines.MOD_ID);


    public static final RegistryObject<SimpleParticleType> CAST_PARTICLES =
            PARTICLE_TYPES.register("cast_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> DEFENCE_PARTICLES =
            PARTICLE_TYPES.register("defence_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> STINKY_PARTICLES =
            PARTICLE_TYPES.register("stinky_particles", () -> new SimpleParticleType(true));



    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
