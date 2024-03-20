package com.josinosle.magicengines.spells.spellcontent.combat;

import com.josinosle.magicengines.spells.Spell;
import com.josinosle.magicengines.spells.spellcontent.SpellCastManaChanges;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SpellDamage extends Spell {
    public SpellDamage(Entity entity, ServerPlayer player){

        SpellCastManaChanges logic = new SpellCastManaChanges();
        if(logic.spellCastable(player,1500)) {
            entity.hurt(DamageSource.MAGIC,10);
            logic.subMana(player,1500);
        }
    }
}


