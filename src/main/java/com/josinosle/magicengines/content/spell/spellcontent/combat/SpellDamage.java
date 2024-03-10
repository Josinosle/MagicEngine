package com.josinosle.magicengines.content.spell.spellcontent.combat;

import com.josinosle.magicengines.content.spell.Spell;
import com.josinosle.magicengines.init.ParticleInit;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import java.util.List;

public class SpellDamage extends Spell {
    public SpellDamage(ServerLevel level, CastVector vector){
        AABB boundBox = new AABB(vector.getX() - 5, vector.getY() - 5, vector.getZ() - 5, vector.getX() + 5, vector.getY() + 5, vector.getZ() + 5);
        List<Entity> entToDamage = level.getEntities(null, boundBox);
        for (Entity i : entToDamage) {
            i.hurt(DamageSource.MAGIC, 10);

            level.getLevel().sendParticles(
                    ParticleTypes.POOF,
                    i.getX(),
                    i.getY()+1,
                    i.getZ(),
                    1,
                    0,
                    0.05,
                    0,
                    0);
        }
    }
}
