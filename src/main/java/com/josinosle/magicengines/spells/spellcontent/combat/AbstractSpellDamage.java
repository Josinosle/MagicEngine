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
    public int triggerCast(ServerPlayer player, ArrayList<Entity> entityList, double manaMultiplier, double effectValue) {
        int manaSpent = 0; // total mana expenditure

        final SpellCastManaChanges logic = new SpellCastManaChanges();
        final int manaAmount = (int) (ServerConfigs.ABSTRACT_SPELL_DAMAGE_REQUIRED_MANA_AMOUNT.get()*manaMultiplier);

        for (Entity entity : entityList) {
            if (logic.spellCastable(player, (int) (manaAmount))) {
                entity.hurt(DamageSource.MAGIC, (float) effectValue);
                logic.subMana(player, (int) (manaAmount));
                manaSpent += manaAmount;
            }
        }
        return(manaSpent);
    }
}


