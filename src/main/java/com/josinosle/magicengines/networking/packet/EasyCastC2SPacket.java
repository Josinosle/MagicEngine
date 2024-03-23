package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.registry.SpellRegistry;
import com.josinosle.magicengines.util.casting.CastVector;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
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
        final NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
           final ServerPlayer player = context.getSender();
           if( player != null) {
               final CastVector vector = new CastVector(x,y,z, player);
               final AABB boundBox = new AABB(vector.getX() - 1, vector.getY() - 1, vector.getZ() - 1, vector.getX() + 1, vector.getY() + 1, vector.getZ() + 1);

               // add entities in a bounding box to working list
               final ArrayList<Entity> entToDamage = new ArrayList<>(player.getLevel().getEntities(null, boundBox));

               SpellRegistry.FART.get().triggerCast(player, entToDamage);
           }
        });
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeDouble(x);
        friendlyByteBuf.writeDouble(y);
        friendlyByteBuf.writeDouble(z);
    }
}
