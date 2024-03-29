package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.mana.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
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

            ClientManaData.set(mana);
        });
    }}

