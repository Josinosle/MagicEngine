package com.josinosle.magicengines.init;


import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
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

        modEventBus.addListener(this::setupLogger);
        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        ParticleInit.PARTICLE_TYPES.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setupLogger(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
    }

}
