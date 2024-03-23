package com.josinosle.magicengines.spells.spellcontent.fun;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.SetDeltaMovementPacket;
import com.josinosle.magicengines.registry.ParticleRegistry;
import com.josinosle.magicengines.registry.SoundRegistry;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.spellcontent.SpellCastManaChanges;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

/**
 * Class to apply the fart spell effects
 *
 * @author Florian Hirson
 */
@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class AbstractSpellFart  extends AbstractSpell{

    public AbstractSpellFart() {

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

    @Override
    public void triggerCast(ServerPlayer player, ArrayList<Entity> entityList) {
        final SpellCastManaChanges logic = new SpellCastManaChanges();
        final int manaAmount = ServerConfigs.FART_REQUIRED_MANA_AMOUNT.get();

        if (logic.spellCastable(player, manaAmount)) {

            for(Entity entityInTheAreaOfSpell: entityList) {
                if(entityInTheAreaOfSpell instanceof LivingEntity livingEntity) {
                    //play fart sound
                    livingEntity.getLevel().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                            SoundRegistry.REVERB_FART.get(), SoundSource.PLAYERS, Long.MAX_VALUE, Long.MIN_VALUE);

                    spawnStinkyParticles(player.getLevel(), livingEntity);

                    //set nausea effect for 400 ticks aka 20 seconds
                    final int duration = 400;
                    final MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.CONFUSION, duration);
                    livingEntity.addEffect(mobEffectInstance);

                    //add jump effect
                    if(livingEntity instanceof Player playerInAreaOfSpell) {
                        Messages.sendToPlayer(new SetDeltaMovementPacket(), (ServerPlayer) player.getLevel().getPlayerByUUID(playerInAreaOfSpell.getUUID()));
                    } else {
                        livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0, 1, 0));
                    }


                }

            }

            logic.subMana(player, manaAmount);
        }
    }
}
