package com.josinosle.magicengines.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetDeltaMovementPacket {


    public SetDeltaMovementPacket() {

    }

    public SetDeltaMovementPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        final NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client action
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if(localPlayer != null) {
                    final Vec3 playerDeltaMovement = localPlayer.getDeltaMovement().add(0, 1, 0);

                localPlayer.setDeltaMovement(playerDeltaMovement);
            }
        });
    }
}
