package com.josinosle.magicengines.content.spell.spellcontent.combat;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.content.spell.spellcontent.SpellCastManaChanges;
import com.josinosle.magicengines.init.ParticleInit;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class PlayerDefence {

    private static ServerPlayer player;
    private static int i;
    private static boolean runEffect;

    public PlayerDefence(ServerPlayer player) {
        SpellCastManaChanges logic = new SpellCastManaChanges();
        if(logic.spellCastable(player,5000)){
            PlayerDefence.player = player;
            runEffect = true;
            i=0;
            logic.subMana(player,5000);
        }



    }

    @SubscribeEvent
    public static void playerTakeDamage(LivingHurtEvent event) {
        if (runEffect && event.getEntity() instanceof Player && i<100) {
            i += (int) (event.getAmount());
            for (double j = 0.6; j <= 1.8; j+=0.6){
                for (int k = 0; k <= 360; k += 60) {
                    // send spawn particle

                    player.getLevel().sendParticles(
                            ParticleInit.DEFENCE_PARTICLES.get(),
                            player.getX(),
                            player.getY() + j,
                            player.getZ(),
                            1,
                            0.75 * Math.cos(k),
                            0.05,
                            0.75 * Math.sin(k),
                            0);
                }
            }

            player.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 5.0F, 0.5F);

            float tempAmount = event.getAmount();
            event.setAmount(tempAmount/10);
            System.out.println(i);
        }
        if(i>=100){
            i = 0;
            runEffect = false;
        }
    }
}

