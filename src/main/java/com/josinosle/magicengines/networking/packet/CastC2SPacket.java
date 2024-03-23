package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.util.casting.NetworkCastLogicHandling;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CastC2SPacket {

    private final double x;
    private final double y;
    private final double z;
    private final double manaEfficiency;

    public CastC2SPacket(double x, double y, double z, double manaEfficiency){
        this.x = x;
        this.y = y;
        this.z = z;
        this.manaEfficiency = manaEfficiency;
    }

    public CastC2SPacket(FriendlyByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.manaEfficiency = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(manaEfficiency);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client action
            ServerPlayer player = context.getSender();
            assert player != null;
            Level level = player.getLevel();

            NetworkCastLogicHandling.handlePlayerSetVectorComboList(new Vec3(x,y,z),level,player,manaEfficiency);
        });
    }
}
