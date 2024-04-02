package com.josinosle.magicengines.spells.spellcontent.combat;

import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.spellcontent.SpellCastManaChanges;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;

public class TesticularTorsionSpell extends AbstractSpell {

    @Override
    public int triggerCast(ServerPlayer player, ArrayList<LivingEntity> entityList, double manaMultiplier, double effectValue) {
        final SpellCastManaChanges logic = new SpellCastManaChanges();
        final int manaAmount = (int) (ServerConfigs.TESTICULAR_TORSION_REQUIRED_MANA_AMOUNT.get() * manaMultiplier);

        if (logic.spellCastable(player, manaAmount) && entityList != null && !entityList.isEmpty()) {
            // entityList.get(0).hurt(DamageSource.MAGIC, (float) effectValue); commented out because incorrect for model

            //add effects for 5 seconds aka 100 ticks
            entityList.get(0).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100));

        }
        return manaAmount;
    }
}
