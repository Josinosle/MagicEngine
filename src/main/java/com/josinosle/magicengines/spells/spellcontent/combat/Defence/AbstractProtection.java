package com.josinosle.magicengines.spells.spellcontent.combat.Defence;

import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.event.ServerPlayerCastingEvent;
import com.josinosle.magicengines.registry.ParticleRegistry;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.spellcontent.SpellCastManaChanges;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class AbstractProtection extends AbstractSpell {

    protected ArrayList<LivingEntity> entityList;
    protected ArrayList<Integer> entityListIDs = new ArrayList<>();

    protected ServerPlayer player;
    protected boolean runEffect;
    protected double manaMultiplier;

    // constructor
    public AbstractProtection() {
        super();
    }

    // trigger effect
    @Override
    public int triggerCast(ServerPlayer pPlayer, ArrayList<LivingEntity> pEntityList, double manaMult, double effectValue){
        player = pPlayer;
        entityList = pEntityList;

        for (LivingEntity entity : entityList) {
            entityListIDs.add(entity.getId());
        }

        manaMultiplier = manaMult;
        runEffect = true;
        MinecraftForge.EVENT_BUS.register(this);
        return (int) ServerConfigs.PLAYER_DEFENSE_REQUIRED_MANA_AMOUNT.get();
    }

    @SubscribeEvent
    public void playerTakeDamage(LivingAttackEvent event) {

        if (runEffect && entityListIDs.contains(event.getEntity().getId()) && eventDamageInDamageSourceList(event.getSource())) {

            final SpellCastManaChanges logic = new SpellCastManaChanges();
            final int manaAmount = (int) (ServerConfigs.PLAYER_DEFENSE_REQUIRED_MANA_AMOUNT.get() * manaMultiplier * event.getAmount());

            if (logic.spellCastable(player, manaAmount)) {

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
                                1,
                                0.75 * Math.cos(k),
                                0,
                                0.75 * Math.sin(k),
                                0.01);
                    }
                }

                // sound effect
                event.getEntity().getLevel().playSound(null, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 5.0F, 0.5F);
                logic.subMana(player, manaAmount);
            } else {
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }
    }

    // unregister this listener if player begins a new cast
    @SubscribeEvent
    public void playerCasting(ServerPlayerCastingEvent event) {
        if (event.getPlayerCasting().getId() == player.getId()) {
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }

    protected boolean eventDamageInDamageSourceList (DamageSource source) {
        // list of possible damage sources
        ArrayList<DamageSource> tempDamageSourceList = new ArrayList<>();
        tempDamageSourceList.add(DamageSource.OUT_OF_WORLD);

        for (DamageSource damageSource : tempDamageSourceList) {
            if (damageSource == source) {
                return true;
            }
        }
        return false;
    }
}

