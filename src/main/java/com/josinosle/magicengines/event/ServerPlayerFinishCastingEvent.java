package com.josinosle.magicengines.event;

import com.josinosle.magicengines.util.casting.CastRune;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

import java.util.LinkedList;

/**
 * Event for handling players beginning to cast
 *
 * @author josinosle
 */
public class ServerPlayerFinishCastingEvent extends Event {
    private final ServerPlayer playerCasting;
    private final Vec3 castingPosition;
    private final LinkedList<CastRune> castRunes;
    public LogicalSide side;

    /**
     * Constructor
     * @param player Player casting
     * @param vec3pos Position of casting player
     */
    public ServerPlayerFinishCastingEvent(ServerPlayer player, Vec3 vec3pos, LogicalSide side, LinkedList<CastRune> castStack) {
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
     * Get method for {@link LinkedList} of {@link CastRune}
     * @return  {@link LinkedList} of {@link CastRune}
     */
    public LinkedList<CastRune> getCastRunes () {return castRunes;}
}
