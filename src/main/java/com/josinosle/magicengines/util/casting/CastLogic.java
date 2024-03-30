package com.josinosle.magicengines.util.casting;

import com.josinosle.magicengines.event.ServerPlayerCastingEvent;
import com.josinosle.magicengines.event.ServerPlayerFinishCastingEvent;
import com.josinosle.magicengines.registry.ParticleRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.LogicalSide;

import java.util.ArrayList;

/**
 * A class to process vector inputs and output a list of rune codes
 *
 * @author josinosle
 */
public class CastLogic {

    private final ArrayList<CastRune> castingStack = new ArrayList<>();
    private CastRune rune = new CastRune();
    private final ServerPlayer playerIdentifier;
    private boolean spellCast;
    private double manaMultTally = 0;
    private int totalStrokes = 0;

    /**
     * {@link CastLogic} constructor
     * @param player    the player on which the cast logic is applicable
     */
    public CastLogic(ServerPlayer player) {
        this.playerIdentifier = player;
    }

    /**
     *
     * @return the server player
     */
    public ServerPlayer getPlayer() {return playerIdentifier;}

    /**
     * Method used to add a new vector to a vector combo list
     *
     * @param vector    the vector input to add a casting geometry point to
     * @param level     the level to cast effects upon
     * @param player    the player entity
     */
    public void setVectorComboList(Vec3 vector, Level level, ServerPlayer player,double manaEfficiency) {

        if (!spellCast) {

            // add vector to array
            rune.addVectorToRune(vector);
            this.manaMultTally += manaEfficiency; this.totalStrokes++;

            // post new cast event to forge subscriber bus
            if(rune.isRuneEmpty() && castingStack.isEmpty()){
                MinecraftForge.EVENT_BUS.post(new ServerPlayerCastingEvent(player, vector, LogicalSide.SERVER));
            }

            // create temp difference vector

            Vec3 tempVector2to1 = rune.getCastVector(-1).subtract(rune.getCastVector(-2));

            // casting particle filler loop
            for (double i = 0; i < tempVector2to1.length(); i += 0.05F) {
                //Spawn Particle

                player.getLevel().sendParticles(ParticleRegistry.CAST_PARTICLES.get(),
                        rune.getCastVector(-2).x() + tempVector2to1.x()/tempVector2to1.length() * i,
                        rune.getCastVector(-2).y() + tempVector2to1.y()/tempVector2to1.length() * i,
                        rune.getCastVector(-2).z() + tempVector2to1.z()/tempVector2to1.length() * i,
                        0,
                        0,
                        0,
                        0,
                        0
                );
            }

            // play casting sound to server
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 0.2F);
        } else {

            // cast spell
            double totalManaEfficiency = manaMultTally/totalStrokes;
            CastHelper.cast(castingStack,vector,player,totalManaEfficiency);
            spellCast = false;
        }
    }

    /**
     *
     * @param level     the level on which the casting effect occurs
     * @param player    the player which the casting logic is based on
     */
    public void calculateCast(Level level, ServerPlayer player) {
        // check vector list isn't empty
        if (rune.isRuneEmpty()) {
            rune = new CastRune();
            castingStack.clear();
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 4.0F, 0.5F);
            spellCast = false;
            player.sendSystemMessage(Component.literal("Current cast cleared").withStyle(ChatFormatting.WHITE));
            return;
        }

        // play rune condition
        if (rune.getRune() == 11){
            // post finish casting event for specific player
            MinecraftForge.EVENT_BUS.post(new ServerPlayerFinishCastingEvent(player, player.position(), LogicalSide.SERVER, castingStack));

            spellCast = true;
        }

        // add current rune to casting stack
        if (!spellCast) {
            castingStack.add(rune);
        }

        // clear non-applicable lists
        rune = new CastRune();
    }
}
