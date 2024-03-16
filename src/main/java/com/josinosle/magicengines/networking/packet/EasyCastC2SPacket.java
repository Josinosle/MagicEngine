package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.content.spell.spellcontent.fun.SpellFart;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EasyCastC2SPacket {
    

    public EasyCastC2SPacket() {

    }

    public EasyCastC2SPacket(FriendlyByteBuf friendlyByteBuf) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        System.out.println("Bean wand handle server side");
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client action
            ServerPlayer player = context.getSender();
            assert player != null;

            new SpellFart(player);
        });
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
    }
}
