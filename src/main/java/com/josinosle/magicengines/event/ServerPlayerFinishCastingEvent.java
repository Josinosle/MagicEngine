package com.josinosle.magicengines.event;

import com.josinosle.magicengines.util.casting.CastRune;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

import java.util.ArrayList;

/**
 * Event for handling players beginning to cast
 *
 * @author josinosle
 */
public class ServerPlayerFinishCastingEvent extends Event {
    private ServerPlayer playerCasting;
    private Vec3 castingPosition;
    private ArrayList<CastRune> castRunes;
    public LogicalSide side;

    /**
     * Constructor
     * @param player Player casting
     * @param vec3pos Position of casting player
     */
    public ServerPlayerFinishCastingEvent(ServerPlayer player, Vec3 vec3pos, LogicalSide side, ArrayList<CastRune> castStack) {
        this.playerCasting = player;
        this.castingPosition = vec3pos;
        this.side = side;
        this.castRunes = castStack;
    }

    /**
     * Get method for player casting
     * @return      ServerPlayer for player who's casting
     */
    public ServerPlayer getPlayerCasting() {return playerCasting;}

    /**
     * Get method for caster position
     * @return      Vec3 position for caster
     */
    public Vec3 getCastingPosition() {return castingPosition;}

    /**
     * Get method for ArrayList of {@link CastRune}
     * @return
     */
    public ArrayList<CastRune> getCastRunes () {return castRunes;}
}
