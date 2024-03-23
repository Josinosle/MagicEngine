package com.josinosle.magicengines.util.cantrip;

import com.josinosle.magicengines.util.casting.CastHelper;
import com.josinosle.magicengines.util.casting.CastRune;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

/**
 * A class to help save cantrip data for the player
 *
 * @author josinosle
 */
public class CantripBuildPlayerHelper {
    private ArrayList<CastRune> cantripCode = new ArrayList<>();
    private ServerPlayer player;
    private int cantripID;

    /**
     * Constructor method for a cantrip code
     *
     * @param cantripCode       String array of the cantrips build code
     * @param player        ServerPlayer object of the cantrip's player
     */
    public CantripBuildPlayerHelper(ArrayList<CastRune> cantripCode, ServerPlayer player, int cantripID){
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
    public void castCantrip(Vec3 vector){
        CastHelper.castSpell(cantripCode, vector, player);

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

