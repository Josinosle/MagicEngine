package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.item.MagicWand;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpawnDrawParticleS2CPacket {

    private final int x;
    private final int y;
    private final int z;

    public SpawnDrawParticleS2CPacket(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SpawnDrawParticleS2CPacket(FriendlyByteBuf buf){
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            //client action
            MagicWand.previousVector = new CastVector(x,y,z);
        });
        return true;
    }
}
