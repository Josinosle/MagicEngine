package com.josinosle.magicengines.init;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.spell.Spell;
import com.josinosle.magicengines.spell.spellcontent.combat.SpellDamage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

public class SpellInit {

    public static final DeferredRegister<Spell> MAGIC_ENGINE_SPELLS = DeferredRegister.create(new ResourceLocation(MagicEngines.MOD_ID, "spells"),MagicEngines.MOD_ID);
    public static final Supplier<IForgeRegistry<Spell>> REGISTRY =
            MAGIC_ENGINE_SPELLS.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<Spell> UNASPECTED_DAMAGE =
            MAGIC_ENGINE_SPELLS.register("unaspected_damage", SpellDamage::new);

    public static void register(IEventBus eventBus) {MAGIC_ENGINE_SPELLS.register(eventBus);
    }

}

