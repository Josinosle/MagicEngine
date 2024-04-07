package com.josinosle.magicengines.spells.spellcontent.combat;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.SpellCastManaChanges;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class Throw extends AbstractSpell {
    public Throw(){
        super();
    }

    @Override
    public int triggerCast(ServerPlayer player, ArrayList<LivingEntity> entityList, Vec3 vector, double manaMultiplier, double effectValue) {
        int manaSpent = 0; // total mana expenditure

        final SpellCastManaChanges logic = new SpellCastManaChanges();
        final int manaAmount = (int) (ServerConfigs.TELEKENETIC_SLAM_REQUIRED_MANA_AMOUNT.get()*manaMultiplier);

        for (Entity entity : entityList) {
            if (logic.spellCastable(player, manaAmount)) {

                // change entity delta movement
                entity.setDeltaMovement(
                        ((entity.position().subtract(player.getPosition(0)).normalize()).multiply(effectValue,effectValue,effectValue))
                );
                System.out.println(entity.getDeltaMovement().x());


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

                // sum mana
                manaSpent += manaAmount;
            }
        }
        return manaSpent;
    }
}
