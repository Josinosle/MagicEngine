package com.josinosle.magicengines.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

/**
 * Event for handling players beginning to cast
 *
 * @author josinosle
 */
public class ServerPlayerFinishCastingEvent extends Event {
    ServerPlayer playerCasting;
    Vec3 castingPosition;
    LogicalSide side;

    /**
     * Constructor
     * @param player Player casting
     * @param vec3pos Position of casting player
     */
    public ServerPlayerFinishCastingEvent(ServerPlayer player, Vec3 vec3pos, LogicalSide side) {
        this.playerCasting = player;
        this.castingPosition = vec3pos;
        this.side = side;
    }

    /**
     * Get method for player casting
     * @return      ServerPlayer for player who's casting
     */
    public ServerPlayer getPlayerCasting() {
        return playerCasting;
    }

    /**
     * Get method for caster position
     * @return      Vec3 position for caster
     */
    public Vec3 getCastingPosition() {
        return castingPosition;
    }
}
