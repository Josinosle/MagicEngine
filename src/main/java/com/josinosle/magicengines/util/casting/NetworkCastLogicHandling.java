package com.josinosle.magicengines.util.casting;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

/**
 * Utility class for handling the networking for players for casting logic
 *
 * @author josinosle
 */
public class NetworkCastLogicHandling {

    private static final ArrayList<CastLogic> worldCasts = new ArrayList<>();

    /**
     * Method for handling a vector combo list addition request packet
     *
     * @param vector        the vector to pass on to the logical handling for casting
     * @param level         the level on which the cast effect occurs
     * @param player        the player responsible for the casting logic
     */
    public static void handlePlayerSetVectorComboList(Vec3 vector, Level level, ServerPlayer player){

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

    /**
     * Method for handling a cast calculation request packet
     *
     * @param level         the level on which the casting logic occurs
     * @param player        the player responsible for the casting logic
     */
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

    /**
     * Method for creating new player cast logic
     *
     * @param player        the player to create a new cast logic handler for
     */
    private static void addNewCastLogic(ServerPlayer player){
        System.out.println("New player cast logic created");
        worldCasts.add(new CastLogic(player));
    }
}

