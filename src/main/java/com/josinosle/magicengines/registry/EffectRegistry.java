package com.josinosle.magicengines.registry;

import com.josinosle.magicengines.MagicEngines;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRegistry {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MagicEngines.MOD_ID);

//    public static final RegistryObject<MobEffect> TESTICULAR_TORSION = MOB_EFFECTS.register("testicular_torsion",
//            () -> new TesticularTorsionEffect(MobEffectCategory.HARMFUL, 11141120));




    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
