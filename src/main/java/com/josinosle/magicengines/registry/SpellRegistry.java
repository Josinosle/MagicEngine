package com.josinosle.magicengines.registry;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.spellcontent.combat.AbstractSpellDamage;
import com.josinosle.magicengines.spells.spellcontent.combat.Defence.ElementalProtection;
import com.josinosle.magicengines.spells.spellcontent.combat.Defence.KineticProtection;
import com.josinosle.magicengines.spells.spellcontent.combat.Defence.MagicProtection;
import com.josinosle.magicengines.spells.spellcontent.combat.Throw;
import com.josinosle.magicengines.spells.spellcontent.fun.YeetSpell;
import com.josinosle.magicengines.spells.spellcontent.utility.virtualblock.VirtualBlock;
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

    public static final RegistryObject<AbstractSpell> KINETIC_DEFENSE = MAGIC_ENGINE_SPELLS.register("kinetic_defense", KineticProtection::new);
    public static final RegistryObject<AbstractSpell> MAGIC_DEFENSE = MAGIC_ENGINE_SPELLS.register("magic_defense", MagicProtection::new);
    public static final RegistryObject<AbstractSpell> ELEMENTAL_DEFENSE = MAGIC_ENGINE_SPELLS.register("elemental_defense", ElementalProtection::new);

    public static final RegistryObject<AbstractSpell> THROW = MAGIC_ENGINE_SPELLS.register("throw", Throw::new);
    public static final RegistryObject<AbstractSpell> YEET = MAGIC_ENGINE_SPELLS.register("yeet", YeetSpell::new);

    public static final RegistryObject<AbstractSpell> VIRTUAL_BLOCK = MAGIC_ENGINE_SPELLS.register("virtual_block", VirtualBlock::new);

    public static void register(IEventBus eventBus) {MAGIC_ENGINE_SPELLS.register(eventBus);
    }
}

