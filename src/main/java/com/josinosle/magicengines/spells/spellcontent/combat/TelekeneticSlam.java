package com.josinosle.magicengines.spells.spellcontent.combat;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.spellcontent.SpellCastManaChanges;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class TelekeneticSlam extends AbstractSpell {
    public TelekeneticSlam(){
        super();
    }

    @Override
    public void triggerCast(ServerPlayer player, ArrayList<Entity> entityList,double manaMultiplier, double effectValue) {
        final SpellCastManaChanges logic = new SpellCastManaChanges();
        final int manaAmount = (int) (ServerConfigs.TELEKENETIC_SLAM_REQUIRED_MANA_AMOUNT.get()*manaMultiplier);

        for (Entity entity : entityList) {
            if (logic.spellCastable(player, manaAmount)) {

                // change entity delta movement
                entity.setDeltaMovement(
                        (entity.getX() - player.getX())*0.1*effectValue,
                        (entity.getY() - player.getY() + 0.2)*0.1*effectValue,
                        (entity.getZ() - player.getZ())*0.1*effectValue);

                // send spawn particle
                player.getLevel().sendParticles(
                        ParticleTypes.EXPLOSION,
                        entity.getX(),
                        entity.getY() + 1,
                        entity.getZ(),
                        1,
                        0,
                        0.05,
                        0,
                        0);

                entity.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 2.0F);

                //sub mana
                logic.subMana(player, manaAmount);
            }
        }
    }
}
