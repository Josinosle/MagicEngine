package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.util.casting.NetworkCastLogicHandling;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CalculateCastC2SPacket {

    public CalculateCastC2SPacket(){

    }

    public CalculateCastC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client action
            ServerPlayer player = context.getSender();
            assert player != null;
            Level level = player.getLevel();

            NetworkCastLogicHandling.handlePlayerCalculateCast(level,player);
        });
    }
}
