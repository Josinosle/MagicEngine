package com.josinosle.magicengines.util.cantrip;

import com.josinosle.magicengines.util.casting.CastHelper;
import com.josinosle.magicengines.util.casting.CastVector;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;

/**
 * A class to help save cantrip data for the player
 *
 * @author josinosle
 */
public class CantripBuildPlayerHelper {
    private ArrayList<Integer> cantripCode = new ArrayList<>();
    private ServerPlayer player;
    private int cantripID;

    /**
     * Constructor method for a cantrip code
     *
     * @param cantripCode       String array of the cantrips build code
     * @param player        ServerPlayer object of the cantrip's player
     */
    public CantripBuildPlayerHelper(ArrayList<Integer> cantripCode, ServerPlayer player, int cantripID){
        this.cantripCode = cantripCode;
        this.player = player;
        this.cantripID = cantripID;

        CompoundTag tag;

    }

    /**
     * Method for casting a cantrip
     *
     * @param vector        CastVector object for the casting position
     */
    public void castCantrip(CastVector vector){
        CastHelper.castSpell(cantripCode, vector, player.getLevel(), player);

    }

    /**
     * Cantrip code set method
     *
     * @param code      Integer ID for the saved cantrip
     */
    public void setCantripID(int code){
        this.cantripID = code;
    }

    /**
     * Get method for cantrip ID
     *
     * @return      Integer ID for the saved cantrip
     */
    public int getCantripID(){
        return this.cantripID;
    }
}

