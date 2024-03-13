package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.client.clientdata.ClientManaData;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import com.josinosle.magicengines.util.castgeometry.NetworkCastLogicHandling;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncManaS2CPacket {


    private final int mana;


    public SyncManaS2CPacket(int mana){
        this.mana = mana;
    }

    public SyncManaS2CPacket(FriendlyByteBuf buf) {
        this.mana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(mana);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client action
            ServerPlayer player = context.getSender();

            ClientManaData.set(mana);
        });
    }}

