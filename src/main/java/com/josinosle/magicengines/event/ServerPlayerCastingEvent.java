package com.josinosle.magicengines.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Event;

/**
 * Event for handling players beginning to cast
 *
 * @author josinosle
 */
public class ServerPlayerCastingEvent extends Event {
    ServerPlayer playerCasting;
    Vec3 castingPosition;

    /**
     * Constructor for player casting
     * @param player player casting
     * @param vec3pos position of player casting
     */
    public ServerPlayerCastingEvent(ServerPlayer player, Vec3 vec3pos) {
        this.playerCasting = player;
        this.castingPosition = vec3pos;
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
