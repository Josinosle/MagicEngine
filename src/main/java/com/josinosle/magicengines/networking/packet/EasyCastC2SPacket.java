package com.josinosle.magicengines.networking.packet;

import com.josinosle.magicengines.registry.SpellRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
               final Vec3 vector = new Vec3(x,y,z);
               final AABB boundBox = new AABB(vector.x() - 1, vector.y() - 1, vector.z() - 1, vector.x() + 1, vector.y() + 1, vector.z() + 1);

               // add entities in a bounding box to working list
               final ArrayList<Entity> entToDamage = new ArrayList<>(player.getLevel().getEntities(null, boundBox));

               SpellRegistry.FART.get().triggerCast(player, entToDamage, 1,1);
           }
        });
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeDouble(x);
        friendlyByteBuf.writeDouble(y);
        friendlyByteBuf.writeDouble(z);
    }
}
