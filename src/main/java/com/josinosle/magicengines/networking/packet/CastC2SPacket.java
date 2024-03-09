package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.util.KeyboardHelper;
import com.josinosle.magicengines.util.castgeometry.CastLogic;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.josinosle.magicengines.util.RaycastHelper.rayTrace;

public class CastC2SPacket {

    private final CastLogic currentCastLogic = new CastLogic();
    private int i = 0;

    public CastC2SPacket(){

    }

    public CastC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
        });
        return true;

    }

    private void spawnParticles(CastVector vector, Level level, Player player) {

        for(float i = 0; i <= 360; i+= 72){
            //Spawn Particle
            level.addAlwaysVisibleParticle(ParticleTypes.SOUL_FIRE_FLAME,
                    vector.getX() + 0.5,
                    vector.getY() + 0.7,
                    vector.getZ() + 0.5,
                    Math.cos(i) * 0.1, 0, Math.sin(i) * 0.1
            );
        }
        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
