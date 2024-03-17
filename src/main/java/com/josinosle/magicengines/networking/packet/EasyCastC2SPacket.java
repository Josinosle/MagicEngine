package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.content.spell.spellcontent.fun.SpellFart;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EasyCastC2SPacket {

    private final double x;
    private final double y;
    private final double z;

    public EasyCastC2SPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public EasyCastC2SPacket(FriendlyByteBuf friendlyByteBuf) {
        this.x = friendlyByteBuf.readDouble();
        this.y = friendlyByteBuf.readDouble();
        this.z = friendlyByteBuf.readDouble();
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        System.out.println("Bean wand handle server side");
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client action
            ServerPlayer player = context.getSender();
            assert player != null;

            new SpellFart(player, new CastVector(x,y,z, player));
        });
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeDouble(x);
        friendlyByteBuf.writeDouble(y);
        friendlyByteBuf.writeDouble(z);
    }
}
