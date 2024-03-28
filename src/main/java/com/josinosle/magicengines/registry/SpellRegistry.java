package com.josinosle.magicengines.registry;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.spellcontent.combat.AbstractSpellDamage;
import com.josinosle.magicengines.spells.spellcontent.combat.Defence.AbstractProtection;
import com.josinosle.magicengines.spells.spellcontent.combat.TelekeneticSlam;
import com.josinosle.magicengines.spells.spellcontent.fun.YeetSpell;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SpellRegistry {

    public static final DeferredRegister<AbstractSpell> MAGIC_ENGINE_SPELLS = DeferredRegister.create(new ResourceLocation(MagicEngines.MOD_ID, "spells"),MagicEngines.MOD_ID);

    public static final Supplier<IForgeRegistry<AbstractSpell>> REGISTRY =
            MAGIC_ENGINE_SPELLS.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<AbstractSpell> UNASPECTED_DAMAGE = MAGIC_ENGINE_SPELLS.register("unaspected_damage", AbstractSpellDamage::new);
    public static final RegistryObject<AbstractSpell> DEFENSE = MAGIC_ENGINE_SPELLS.register("defense", AbstractProtection::new);
    public static final RegistryObject<AbstractSpell> THROW = MAGIC_ENGINE_SPELLS.register("throw", TelekeneticSlam::new);
    public static final RegistryObject<AbstractSpell> YEET = MAGIC_ENGINE_SPELLS.register("yeet", YeetSpell::new);

    public static void register(IEventBus eventBus) {MAGIC_ENGINE_SPELLS.register(eventBus);
    }
}

