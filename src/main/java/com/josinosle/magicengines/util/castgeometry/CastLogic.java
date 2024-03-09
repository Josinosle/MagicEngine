package com.josinosle.magicengines.util.castgeometry;

import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.SpawnDrawParticleS2CPacket;
import com.josinosle.magicengines.util.CastHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;


public class CastLogic {
    private static final ArrayList<String> castingStack = new ArrayList<>();
    public static final ArrayList<CastVector> vectorComboList = new ArrayList<>();
    private static final ArrayList<String> currentlyDrawnRune = new ArrayList<>();
    private static CastVector startVector;

    public CastLogic(){
    }

    public static void setVectorComboList(CastVector vector, Level level, ServerPlayer player){

        if(vectorComboList.isEmpty()){
            startVector = vector;
        }

        vectorComboList.add(vector);

        if(vectorComboList.size()>2){
            currentlyDrawnRune.add(dotProduct(vectorComboList.get(vectorComboList.size()-3),vectorComboList.get(vectorComboList.size()-1),vectorComboList.get(vectorComboList.size()-2)));
        }

        System.out.println(currentlyDrawnRune);

        if(String.join("", currentlyDrawnRune).equals("AD")){
            CastHelper.castSpell(castingStack, startVector, level);
            System.out.println("Cast");
        }
    }

    public static void calculateCast(Level level, ServerPlayer player){
        if(vectorComboList.isEmpty()){
            currentlyDrawnRune.clear(); vectorComboList.clear(); castingStack.clear();
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 2.0F, 2.0F);
            return;
        }
        else if(vectorComboList.size()<3){
            currentlyDrawnRune.clear(); vectorComboList.clear(); return;
        }

        /*
        for(int i = 1; i < vectorComboList.size(); i++) {
            //spawnParticles(vectorComboList.get(i-1),vectorComboList.get(i),level, player, ParticleTypes.SOUL_FIRE_FLAME);
        }
        */

        castingStack.add(String.join("",currentlyDrawnRune)); vectorComboList.clear();
        castingStack.forEach(System.out::println);
        currentlyDrawnRune.clear();
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
        if (dotProduct<=-0.5) return "D";
        if (dotProduct<=0) return "C";
        if (dotProduct<=0.5) return "B";
        return "A";
    }

    @OnlyIn(Dist.CLIENT)
    private void spawnParticles(CastVector vector1, CastVector vector2, Level level, Player player, ParticleOptions particle) {
        CastVector tempVector2to1 = new CastVector(vector1.getX()- vector2.getX(), vector1.getY()- vector2.getY(), vector1.getZ()- vector2.getZ());

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
