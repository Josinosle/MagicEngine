package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.util.castgeometry.CastLogic;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import com.josinosle.magicengines.util.castgeometry.CurrentCasts;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public class CalculateCastC2SPacket {

    public CalculateCastC2SPacket(){

    }

    public CalculateCastC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client action
            ServerPlayer player = context.getSender();
            Level level = player.getLevel();

            CurrentCasts.handlePlayerCalculateCast(level,player);
        });
        return true;
    }
}
