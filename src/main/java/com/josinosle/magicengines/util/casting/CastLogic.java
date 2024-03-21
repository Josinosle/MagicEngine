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

    private final ArrayList<Integer> castingStack = new ArrayList<>();
    private final ArrayList<CastVector> vectorComboList = new ArrayList<>();
    private final ArrayList<Integer> currentlyDrawnRune = new ArrayList<>();
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
            vectorComboList.add(vector);

            // casting on block particles
            player.getLevel().sendParticles(ParticleRegistry.CAST_PARTICLES.get(),
                    vectorComboList.get(vectorComboList.size() - 1).getX(),
                    vectorComboList.get(vectorComboList.size() - 1).getY(),
                    vectorComboList.get(vectorComboList.size() - 1).getZ(),
                    0, 0, 0, 0, 0
            );


            // add rune type to currently drawn rune
            if (vectorComboList.size() > 2) {
                currentlyDrawnRune.add(dotProduct(vectorComboList.get(vectorComboList.size() - 3), vectorComboList.get(vectorComboList.size() - 1), vectorComboList.get(vectorComboList.size() - 2)));
            }

            // create temp difference vector
            CastVector tempVector2to1 = new CastVector(
                    vectorComboList.get(vectorComboList.size() - 1).getX() - vectorComboList.get(vectorComboList.size() - 2).getX(),
                    vectorComboList.get(vectorComboList.size() - 1).getY() - vectorComboList.get(vectorComboList.size() - 2).getY(),
                    vectorComboList.get(vectorComboList.size() - 1).getZ() - vectorComboList.get(vectorComboList.size() - 2).getZ(),
                    player
            );

            // casting particle filler loop
            for (float i = 0; i < 1; i += 0.01F) {
                //Spawn Particle
                player.getLevel().sendParticles(ParticleRegistry.CAST_PARTICLES.get(),
                        vectorComboList.get(vectorComboList.size() - 2).getX() + tempVector2to1.getX() * i,
                        vectorComboList.get(vectorComboList.size() - 2).getY() + tempVector2to1.getY() * i,
                        vectorComboList.get(vectorComboList.size() - 2).getZ() + tempVector2to1.getZ() * i,
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
        if (vectorComboList.isEmpty()) {
            currentlyDrawnRune.clear();
            vectorComboList.clear();
            castingStack.clear();
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 4.0F, 0.5F);
            spellCast = false;
            player.sendSystemMessage(Component.literal("Current cast cleared").withStyle(ChatFormatting.WHITE));
            return;
        }

        // check vector list validity
        else if (vectorComboList.size() < 3) {
            currentlyDrawnRune.clear();
            vectorComboList.clear();
            return;
        }

        // play rune condition
        if (isCastingRune(currentlyDrawnRune)) {
            spellCast = true;
        }

        // add current rune to casting stack
        if (!spellCast) {
            // clever loop for concatenating an int like a string
            int codeToAdd = 0;
            for (int i : currentlyDrawnRune){
                codeToAdd = 10*codeToAdd + i;
            }
            castingStack.add(codeToAdd);
        }

        // clear non-applicable lists
        vectorComboList.clear();
        currentlyDrawnRune.clear();
    }

    /**
     * Method to calculate the dot product of two vectors relative to an origin point
     *
     * @param vector1   the first logical vector
     * @param vector2   the second logical vector
     * @param vectorOrigin  the origin point for the logical vectors
     * @return a string variable containing the alphabetic character represented by the dot product
     */
    private static int dotProduct(CastVector vector1, CastVector vector2, CastVector vectorOrigin) {

        // calculate 2 working vectors
        CastVector calcVector1 = vector1.vectorDifference(vectorOrigin);
        CastVector calcVector2 = vector2.vectorDifference(vectorOrigin);

        // calc dot product
        float xProd = (float) (calcVector1.getX() * calcVector2.getX());
        float yProd = (float) (calcVector1.getY() * calcVector2.getY());
        float zProd = (float) (calcVector1.getZ() * calcVector2.getZ());
        float dotProduct = (xProd + yProd + zProd) / (calcVector1.modulus() * calcVector2.modulus());

        // return a character
        if (dotProduct <= -0.85) return 1;
        if (dotProduct <= -0.15) return 4;
        if (dotProduct <= 0.15) return 3;
        if (dotProduct <= 0.85) return 2;
        return 1;
    }

    /**
     * Method to check rune is the cast rune
     * @param currentlyDrawnRune        the integer array list of the current rune
     * @return      boolean of if the rune is correct
     */
    private static boolean isCastingRune(ArrayList<Integer> currentlyDrawnRune){
        int sum = 0;
        for (int i : currentlyDrawnRune){
            sum += i;
        }
        return sum == 1;
    }
}
