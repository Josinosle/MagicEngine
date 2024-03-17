package com.josinosle.magicengines.content.spell.spellcontent.fun;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.content.spell.Spell;
import com.josinosle.magicengines.content.spell.spellcontent.SpellCastManaChanges;
import com.josinosle.magicengines.init.SoundInit;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * Class to apply the fart spell effects
 *
 * @author Florian Hirson
 */
@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class SpellFart extends Spell {

    /**
     * Constructor used to apply the spell actions
     *
     * @param player the player that casts the spell
     * @param vector the vector used to apply an effect to the target of the spell
     */
    public SpellFart(ServerPlayer player, CastVector vector) {
        final SpellCastManaChanges logic = new SpellCastManaChanges();

        if (logic.spellCastable(player,690)) {
            //Area of effect
            final AABB boundBox = new AABB(vector.getX() - 3, vector.getY() - 3, vector.getZ() - 3, vector.getX() + 3, vector.getY() + 3, vector.getZ() + 3);

            //get the entities in the area to apply the spell effects
            //first arg is null because we don't want to ignore an entity
            List<Entity> entitiesInTheAreaOfSpell = player.getLevel().getEntities(null, boundBox);

            for(Entity entity: entitiesInTheAreaOfSpell) {
                if(entity instanceof LivingEntity livingEntity) {
                    //play fart sound
                    entity.getLevel().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                            SoundInit.REVERB_FART.get(), SoundSource.PLAYERS, Long.MAX_VALUE, Long.MIN_VALUE);

                    //set nausea effect for 400 ticks aka 20 seconds
                    final int duration = 400;
                    final MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.CONFUSION, duration);
                    livingEntity.addEffect(mobEffectInstance);
                }

            }
        }

    }
}
