package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.util.casting.CastVector;
import com.josinosle.magicengines.util.casting.NetworkCastLogicHandling;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CastC2SPacket {

    private final double x;
    private final double y;
    private final double z;

    public CastC2SPacket(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CastC2SPacket(FriendlyByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client action
            ServerPlayer player = context.getSender();
            assert player != null;
            Level level = player.getLevel();

            NetworkCastLogicHandling.handlePlayerSetVectorComboList(new CastVector(x,y,z, player),level,player);
        });
    }
}
