package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.spells.spellcontent.fun.SpellFart;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
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
           final ServerPlayer player = context.getSender();
           if( player != null) {
               ServerLevel level = player.getLevel();

               new SpellFart(level, player, new CastVector(x,y,z, player));
           }
        });
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeDouble(x);
        friendlyByteBuf.writeDouble(y);
        friendlyByteBuf.writeDouble(z);
    }
}
