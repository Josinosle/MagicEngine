package com.josinosle.magicengines.util.casting;

import com.josinosle.magicengines.registry.ParticleRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

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

    /**
     *
     * @param player    the player on which the cast logic is applicable
     */
    public CastLogic(ServerPlayer player) {
        this.playerIdentifier = player;
    }

    /**
     *
     * @return the server player
     */
    public ServerPlayer getPlayer() {
        return playerIdentifier;
    }

    /**
     * Method used to add a new vector to a vector combo list
     *
     * @param vector    the vector input to add a casting geometry point to
     * @param level     the level to cast effects upon
     * @param player    the player entity
     */
    public void setVectorComboList(CastVector vector, Level level, ServerPlayer player) {

        if (!spellCast) {

            // add vector to array
            rune.addVectorToRune(vector);

            // play initiating rune noise
            if(rune.isRuneEmpty() && castingStack.isEmpty()){
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 0.2F);
            }

            // casting on block particles
            player.getLevel().sendParticles(ParticleRegistry.CAST_PARTICLES.get(),
                    rune.getCastVector(-1).getX(),
                    rune.getCastVector(-1).getY(),
                    rune.getCastVector(-1).getZ(),
                    0, 0, 0, 0, 0
            );

            // create temp difference vector
            CastVector tempVector2to1 = new CastVector(
                    rune.getCastVector(-1).getX() - rune.getCastVector(-2).getX(),
                    rune.getCastVector(-1).getY() - rune.getCastVector(-2).getY(),
                    rune.getCastVector(-1).getZ() - rune.getCastVector(-2).getZ(),
                    player
            );

            // casting particle filler loop
            for (float i = 0; i < tempVector2to1.modulus(); i += 0.1F) {
                //Spawn Particle

                player.getLevel().sendParticles(ParticleRegistry.CAST_PARTICLES.get(),
                        rune.getCastVector(-2).getX() + tempVector2to1.getX()/tempVector2to1.modulus() * i,
                        rune.getCastVector(-2).getY() + tempVector2to1.getY()/tempVector2to1.modulus() * i,
                        rune.getCastVector(-2).getZ() + tempVector2to1.getZ()/tempVector2to1.modulus() * i,
                        0, 0, 0, 0, 0
                );
            }

            // play casting sound to server
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 0.2F);
        } else {

            // cast spell
            CastHelper.castSpell(castingStack, vector, (ServerLevel) level, player);
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
        if (rune.getRune() == 1){
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
