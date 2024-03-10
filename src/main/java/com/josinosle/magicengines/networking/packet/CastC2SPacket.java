package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.util.castgeometry.CastVector;
import com.josinosle.magicengines.util.castgeometry.CurrentCasts;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CastC2SPacket {

    private final int x;
    private final int y;
    private final int z;

    public CastC2SPacket(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CastC2SPacket(FriendlyByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client action
            ServerPlayer player = context.getSender();
            assert player != null;
            Level level = player.getLevel();

            CurrentCasts.handlePlayerSetVectorComboList(new CastVector(x,y,z, player),level,player);
        });
    }
}
