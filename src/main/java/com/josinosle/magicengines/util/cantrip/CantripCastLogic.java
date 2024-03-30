package com.josinosle.magicengines.util.cantrip;

import com.josinosle.magicengines.event.ServerPlayerCastingEvent;
import com.josinosle.magicengines.util.casting.CastRune;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.LogicalSide;

public class CantripCastLogic {

    private CastRune rune = new CastRune();
    private final ServerPlayer playerIdentifier;
    private boolean isCasting = false;

    /**
     * {@link CantripCastLogic} constructor
     * @param player    the player on which the cast logic is applicable
     */
    public CantripCastLogic(ServerPlayer player) {
        this.playerIdentifier = player;
    }

    /**
     *
     * @return the server player
     */
    public ServerPlayer getPlayer() {return playerIdentifier;}

    public void setVectorComboList(Vec3 vector, Level level, ServerPlayer player, double manaEfficiency) {

        if(!isCasting) {
            // add vector to array
            rune.addVectorToRune(vector);

            // post new cast event to forge subscriber bus
            if (rune.isRuneEmpty()) {
                MinecraftForge.EVENT_BUS.post(new ServerPlayerCastingEvent(player, vector, LogicalSide.SERVER));
            }

            // create temp difference vector

            Vec3 tempVector2to1 = rune.getCastVector(-1).subtract(rune.getCastVector(-2));

            // casting particle filler loop
            for (double i = 0; i < tempVector2to1.length(); i += 0.05F) {
                //Spawn Particle

                player.getLevel().sendParticles(ParticleTypes.FLAME,
                        rune.getCastVector(-2).x() + tempVector2to1.x() / tempVector2to1.length() * i,
                        rune.getCastVector(-2).y() + tempVector2to1.y() / tempVector2to1.length() * i,
                        rune.getCastVector(-2).z() + tempVector2to1.z() / tempVector2to1.length() * i,
                        0,
                        0,
                        0,
                        0,
                        0
                );
            }

            // play casting sound to server
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 0.2F);

            // cast spell
            if (rune.getRune() != 0) {
                isCasting = true;
            }
        }
        // casting logic
        else {
            player.getCapability(PlayerCantripProvider.PLAYER_CANTRIP).ifPresent(cantrip -> {

                // cast cantrip
                cantrip.castCantrip(vector, player, manaEfficiency, rune.getRune() - 1);
                System.out.println("Cantrip Cast");

            });
            this.rune = new CastRune();
            isCasting = false;
        }
    }
}
