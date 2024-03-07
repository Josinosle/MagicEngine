package com.josinosle.magicengines.api.runecast;

import com.josinosle.magicengines.init.ParticleInit;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import java.util.ArrayList;


public class castStack{

    private final ArrayList<castVector> vectorComboList;
    private final ArrayList<String> currentlyDrawnRune;
    private final ArrayList<String> castingStack;
    public static byte isCasting;
    private ParticleOptions particle;

    public castStack(){
        vectorComboList = new ArrayList<>();
        currentlyDrawnRune = new ArrayList<>();
        castingStack = new ArrayList<>();
    }

    public void setVectorComboList(castVector vector,Level level, Player player){
        vectorComboList.add(vector);
        isCasting = 1;
        particle = ParticleInit.CAST_PARTICLES.get();
        if(vectorComboList.size()>1){
            spawnParticles(vectorComboList.get(vectorComboList.size()-2),vectorComboList.get(vectorComboList.size()-1),level, player,particle);
        }
    }

    public void calculateCast(Level level, Player player){
        isCasting = 0;
        if(vectorComboList.isEmpty()){
            currentlyDrawnRune.clear(); vectorComboList.clear(); castingStack.clear();
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 2.0F, 2.0F);
            return;
        }
        else if(vectorComboList.size()<3){
            currentlyDrawnRune.clear(); vectorComboList.clear(); return;
        }
        particle = ParticleTypes.SOUL_FIRE_FLAME;
        for(int i = 1; i < vectorComboList.size(); i++) {
            if(i>1){
                currentlyDrawnRune.add(dotProduct(vectorComboList.get(i-2),vectorComboList.get(i),vectorComboList.get(i-1)));
            }
            spawnParticles(vectorComboList.get(i-1),vectorComboList.get(i),level, player, particle);
        }

        castingStack.add(String.join("",currentlyDrawnRune)); vectorComboList.clear(); currentlyDrawnRune.clear();
        castingStack.forEach(System.out::println);
    }

    private String dotProduct(castVector vector1, castVector vector2, castVector vectorOrigin){
        // calculate 2 working vectors
        castVector calcVector1 = vector1.vectorDifference(vectorOrigin);
        castVector calcVector2 = vector2.vectorDifference(vectorOrigin);

        // calc dot product
        float xProd = (float) (calcVector1.getX()*calcVector2.getX());
        float yProd = (float) (calcVector1.getY()*calcVector2.getY());
        float zProd = (float) (calcVector1.getZ()*calcVector2.getZ());
        float dotProduct = (xProd+yProd+zProd)/(calcVector1.modulus() * calcVector2.modulus());

        //validity checker for range pi/2
        return characterOutput(dotProduct);
    }

    //return a string output
    private String characterOutput(float dotProduct){
        if (dotProduct<=-0.5) return "D";
        if (dotProduct<=0) return "C";
        if (dotProduct<=0.5) return "B";
        return "A";
    }

    private void spawnParticles(castVector vector1, castVector vector2, Level level, Player player, ParticleOptions particle) {
        castVector tempVector2to1 = new castVector(vector1.getX()- vector2.getX(), vector1.getY()- vector2.getY(), vector1.getZ()- vector2.getZ());

        for(float i = 0; i < 1; i+= 0.01F){
            //Spawn Particle
            level.addAlwaysVisibleParticle(particle,
                    vector2.getX() + tempVector2to1.getX()*i + 0.5,
                    vector2.getY() + tempVector2to1.getY()*i + 0.5,
                    vector2.getZ() + tempVector2to1.getZ()*i + 0.5,
                    0, 0, 0
            );
        }
        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

}
