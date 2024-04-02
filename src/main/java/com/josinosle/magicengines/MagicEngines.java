package com.josinosle.magicengines;


import com.josinosle.magicengines.config.ClientConfigs;
import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MagicEngines.MOD_ID)
public class MagicEngines
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "magicengines";

    public MagicEngines() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfigs.SPEC, String.format("%s-client.toml", MOD_ID));
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfigs.SPEC, String.format("%s-server.toml", MOD_ID));

        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        ParticleRegistry.PARTICLE_TYPES.register(modEventBus);
        SpellRegistry.MAGIC_ENGINE_SPELLS.register(modEventBus);
        EffectRegistry.MOB_EFFECTS.register(modEventBus);
        SoundRegistry.register(modEventBus);
        EntityRegistry.ENTITY_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        Messages.register();
    }

    private void setup(final FMLCommonSetupEvent event){
    }
}