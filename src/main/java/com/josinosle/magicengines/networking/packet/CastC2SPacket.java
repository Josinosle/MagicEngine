package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.init.ParticleInit;
import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.util.castgeometry.CastLogic;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import com.josinosle.magicengines.util.castgeometry.CurrentCasts;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
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

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client action
            ServerPlayer player = context.getSender();
            Level level = player.getLevel();

            CurrentCasts.handlePlayerSetVectorComboList(new CastVector(x,y,z, player),level,player);
        });
        return true;
    }
}
