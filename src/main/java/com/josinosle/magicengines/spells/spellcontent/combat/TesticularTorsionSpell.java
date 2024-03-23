package com.josinosle.magicengines.spells.spellcontent.combat;

import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.spellcontent.SpellCastManaChanges;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;

public class TesticularTorsionSpell extends AbstractSpell {
    @Override
    public void triggerCast(ServerPlayer player, ArrayList<Entity> entityList) {
        final SpellCastManaChanges logic = new SpellCastManaChanges();
        final int manaAmount = ServerConfigs.TESTICULAR_TORSION_REQUIRED_MANA_AMOUNT.get();

        if (logic.spellCastable(player, manaAmount) && entityList != null && !entityList.isEmpty()) {
            entityList.get(0).hurt(DamageSource.MAGIC, 10);

            //add effects for 5 seconds aka 100 ticks
            if (entityList.get(0) instanceof LivingEntity livingEntity){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100));
            }
        }
    }
}
