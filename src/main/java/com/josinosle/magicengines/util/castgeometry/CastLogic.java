package com.josinosle.magicengines.util.castgeometry;

import com.josinosle.magicengines.init.ParticleInit;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

import java.util.ArrayList;


public class CastLogic {
    private final ArrayList<String> castingStack = new ArrayList<>();
    private final ArrayList<CastVector> vectorComboList = new ArrayList<>();
    private final ArrayList<String> currentlyDrawnRune = new ArrayList<>();
    private final ServerPlayer playerIdentifier;
    private boolean spellCast;

    public CastLogic(ServerPlayer player){
        this.playerIdentifier = player;
    }

    public ServerPlayer getPlayer(){
        return playerIdentifier;
    }

    public ArrayList<String> getCastingStack(){
        return castingStack;
    }

    public void setVectorComboList(CastVector vector, Level level, ServerPlayer player){

        if(!spellCast) {

            // add vector to array
            vectorComboList.add(vector);

            // casting on block particles
            player.getLevel().sendParticles(ParticleInit.CAST_PARTICLES.get(),
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
                player.getLevel().sendParticles(ParticleInit.CAST_PARTICLES.get(),
                        vectorComboList.get(vectorComboList.size() - 2).getX() + tempVector2to1.getX() * i,
                        vectorComboList.get(vectorComboList.size() - 2).getY() + tempVector2to1.getY() * i,
                        vectorComboList.get(vectorComboList.size() - 2).getZ() + tempVector2to1.getZ() * i,
                        0, 0, 0, 0, 0
                );
            }

            // play casting sound to server
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 0.2F);
        }
        else{
            System.out.println("Spell Cast at vector");
            CastHelper.castSpell(castingStack, vector, (ServerLevel) level, player);

        }
        }

    public void calculateCast(Level level, ServerPlayer player){
        // check vector list isn't empty
        if(vectorComboList.isEmpty()){
            currentlyDrawnRune.clear(); vectorComboList.clear(); castingStack.clear();
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 4.0F, 0.5F);
            spellCast = false;
            return;
        }

        // check vector list validity
        else if(vectorComboList.size()<3){
            currentlyDrawnRune.clear(); vectorComboList.clear();
            return;
        }

        // play rune condition
        if(String.join("", currentlyDrawnRune).equals("AB")){
            spellCast=true;
        }

        // add current rune to casting stack
        if(!spellCast) {
            castingStack.add(String.join("", currentlyDrawnRune));
            System.out.println(currentlyDrawnRune);
        }

        // clear non-applicable lists
        vectorComboList.clear(); currentlyDrawnRune.clear();
    }

    private static String dotProduct(CastVector vector1, CastVector vector2, CastVector vectorOrigin){
        // calculate 2 working vectors
        CastVector calcVector1 = vector1.vectorDifference(vectorOrigin);
        CastVector calcVector2 = vector2.vectorDifference(vectorOrigin);

        // calc dot product
        float xProd = (float) (calcVector1.getX()*calcVector2.getX());
        float yProd = (float) (calcVector1.getY()*calcVector2.getY());
        float zProd = (float) (calcVector1.getZ()*calcVector2.getZ());
        float dotProduct = (xProd+yProd+zProd)/(calcVector1.modulus() * calcVector2.modulus());

        // return a character
        if (dotProduct<=-0.33) return "C";
        if (dotProduct<=0.33) return "B";
        return "A";
    }
}
