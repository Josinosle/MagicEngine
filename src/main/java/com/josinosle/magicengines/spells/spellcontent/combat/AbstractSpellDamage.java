package com.josinosle.magicengines.spells.spellcontent.combat;

import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.spellcontent.SpellCastManaChanges;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;

public class AbstractSpellDamage extends AbstractSpell {
    public AbstractSpellDamage() {}

    @Override
    public void triggerCast(ServerPlayer player, Entity entity, ArrayList<Entity> entityList) {
        final SpellCastManaChanges logic = new SpellCastManaChanges();
        final int manaAmount = ServerConfigs.ABSTRACT_SPELL_DAMAGE_REQUIRED_MANA_AMOUNT.get();

        if (logic.spellCastable(player, manaAmount)) {
            entity.hurt(DamageSource.MAGIC, 10);
            logic.subMana(player, manaAmount);
        }
    }
}


