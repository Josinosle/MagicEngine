package com.josinosle.magicengines.spells.spellcontent.combat;

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
        SpellCastManaChanges logic = new SpellCastManaChanges();
        if (logic.spellCastable(player, 1500)) {
            entity.hurt(DamageSource.MAGIC, 10);
            logic.subMana(player, 1500);
        }
    }
}


