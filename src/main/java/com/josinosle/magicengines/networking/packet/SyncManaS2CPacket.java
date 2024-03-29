package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.mana.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncManaS2CPacket {


    private final int mana;
    private final int maxMana;


    public SyncManaS2CPacket(int mana, int maxMana){
        this.mana = mana;
        this.maxMana = maxMana;
    }

    public SyncManaS2CPacket(FriendlyByteBuf buf) {
        this.mana = buf.readInt();
        this.maxMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(mana);
        buf.writeInt(maxMana);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client action

            ClientManaData.setMana(mana);
            ClientManaData.setMaxMana(maxMana);
        });
    }}

