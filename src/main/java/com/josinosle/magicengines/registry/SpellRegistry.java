package com.josinosle.magicengines.registry;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.spells.Spell;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class SpellRegistry {

    public static final DeferredRegister<Spell> MAGIC_ENGINE_SPELLS = DeferredRegister.create(new ResourceLocation(MagicEngines.MOD_ID, "spells"),MagicEngines.MOD_ID);

    public static final Supplier<IForgeRegistry<Spell>> REGISTRY =
            MAGIC_ENGINE_SPELLS.makeRegistry(RegistryBuilder::new);

    //public static final RegistryObject<Spell> UNASPECTED_DAMAGE =
       //     MAGIC_ENGINE_SPELLS.register("unaspected_damage", SpellDamage::new());

    public static void register(IEventBus eventBus) {MAGIC_ENGINE_SPELLS.register(eventBus);
    }

}

