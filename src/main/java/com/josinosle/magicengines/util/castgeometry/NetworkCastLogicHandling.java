package com.josinosle.magicengines.util.castgeometry;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class NetworkCastLogicHandling {

    private static final ArrayList<CastLogic> worldCasts = new ArrayList<>();

    public static void handlePlayerSetVectorComboList(CastVector vector,Level level, ServerPlayer player){

        // check current cast logics for player match
        for(CastLogic i : worldCasts){
            if(i.getPlayer().getId() == player.getId()){
                i.setVectorComboList(vector,level,player);
                return;
            }
        }

        addNewCastLogic(player);
        handlePlayerSetVectorComboList(vector, level, player);
    }

    public static void handlePlayerCalculateCast(Level level,ServerPlayer player){

        // check current cast logics for player match
        for(CastLogic i : worldCasts){
            if(i.getPlayer().getId() == player.getId()){
                i.calculateCast(level,player);
                return;
            }
        }

        addNewCastLogic(player);
        handlePlayerCalculateCast(level, player);
    }

    private static void addNewCastLogic(ServerPlayer player){
        System.out.println("New player cast logic created");
        worldCasts.add(new CastLogic(player));
    }

    public static ArrayList<ServerPlayer> getWorldPlayers(){
        ArrayList<ServerPlayer> CurrentPlayers = new ArrayList<>();
        for(CastLogic i : worldCasts){
            CurrentPlayers.add(i.getPlayer());
        }
        return CurrentPlayers;
    }
}

