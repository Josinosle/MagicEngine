package com.josinosle.magicengines.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundSyncMana {

    private int playerMana = 0;


    public ClientboundSyncMana(final int playerMana) {
        //Server side only
        this.playerMana = playerMana;
    }

    public ClientboundSyncMana(FriendlyByteBuf buf) {
        playerMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.playerMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            //ClientMagicData.setMana(playerMana);
        });
        return true;
    }



}
