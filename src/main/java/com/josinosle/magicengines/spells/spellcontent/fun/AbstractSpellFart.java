package com.josinosle.magicengines.spells.spellcontent.fun;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.SetDeltaMovementPacket;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.spellcontent.SpellCastManaChanges;
import com.josinosle.magicengines.registry.ParticleRegistry;
import com.josinosle.magicengines.registry.SoundRegistry;
import com.josinosle.magicengines.util.casting.CastVector;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to apply the fart spell effects
 *
 * @author Florian Hirson
 */
@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class AbstractSpellFart {

    /**
     * Constructor used to apply the spell actions
     *
     * @param player the player that casts the spell
     * @param vector the vector used to apply an effect to the target of the spell
     */
    public AbstractSpellFart(final ServerLevel level, final ServerPlayer player, final CastVector vector) {
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
                            SoundRegistry.REVERB_FART.get(), SoundSource.PLAYERS, Long.MAX_VALUE, Long.MIN_VALUE);

                    spawnStinkyParticles(level, entity);

                    //set nausea effect for 400 ticks aka 20 seconds
                    final int duration = 400;
                    final MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.CONFUSION, duration);
                    livingEntity.addEffect(mobEffectInstance);

                    //add jump effect
                    if(livingEntity instanceof Player playerInAreaOfSpell) {
                        Messages.sendToPlayer(new SetDeltaMovementPacket(), (ServerPlayer) level.getPlayerByUUID(playerInAreaOfSpell.getUUID()));
                    } else {
                        livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0, 1, 0));
                    }


                }

            }

            logic.subMana(player,690);
        }
    }

    private void spawnStinkyParticles(final ServerLevel level, final Entity entity) {
        for(int i = 0; i < 360; i++) {
            if(i % 20 == 0) {
                final int count = 1;
                final double deltaX = 1;
                final double deltaY = 0.75 * Math.cos(i);
                final double deltaZ = 0.05;
                final double speed = 0.75 * Math.sin(i);

                level.getLevel().sendParticles(
                        ParticleRegistry.STINKY_PARTICLES.get(),
                        entity.getX(),
                        entity.getY() + 0.25,
                        entity.getZ(),
                        count,
                        deltaX,
                        deltaY,
                        deltaZ,
                        speed);
            }
        }
    }
}
