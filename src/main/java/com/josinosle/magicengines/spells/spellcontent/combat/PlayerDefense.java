package com.josinosle.magicengines.spells.spellcontent.combat;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.registry.ParticleRegistry;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.spellcontent.SpellCastManaChanges;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class PlayerDefense extends AbstractSpell {

    private static ArrayList<Entity> entityList;
    private static ServerPlayer player;
    private static int i;
    private static boolean runEffect;

    // constructor
    public PlayerDefense() {
        super();
    }

    // trigger effect
    @Override
    public void triggerCast(ServerPlayer player,Entity entity, ArrayList<Entity> entityList){
        PlayerDefense.player = player;
        PlayerDefense.entityList = entityList;
        runEffect = true;
        i=0;
    }

    @SubscribeEvent
    public static void playerTakeDamage(LivingAttackEvent event) {
        if (runEffect && entityList.contains(event.getEntity()) && i<100 && (
                event.getSource() != DamageSource.DROWN ||
                event.getSource() != DamageSource.STARVE ||
                event.getSource() != DamageSource.LAVA)) {

            final SpellCastManaChanges logic = new SpellCastManaChanges();
            final int manaAmount = ServerConfigs.PLAYER_DEFENSE_REQUIRED_MANA_AMOUNT.get();

            if(logic.spellCastable(player, manaAmount) && event.getAmount()<=20) {

                // cancel damage event
                event.setCanceled(true);



                // particle spawning
                for (double j = 0.6; j <= 1.8; j += 0.6) {
                    for (int k = 0; k <= 360; k += 60) {

                        // send spawn particle
                        player.getLevel().sendParticles(
                                ParticleRegistry.DEFENSE_PARTICLES.get(),
                                event.getEntity().getX(),
                                event.getEntity().getY() + j,
                                event.getEntity().getZ(),
                                0,
                                0.75 * Math.cos(k),
                                0,
                                0.75 * Math.sin(k),
                                0);
                    }
                }

                // sound effect
                event.getEntity().getLevel().playSound(null, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 5.0F, 0.5F);
                logic.subMana(player, manaAmount);
            }
            if(event.getAmount()>20){
                runEffect = false;
            }
        }
    }
}

