package com.josinosle.magicengines.networking;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.networking.packet.CalculateCastC2SPacket;
import com.josinosle.magicengines.networking.packet.CastC2SPacket;
import com.josinosle.magicengines.networking.packet.SyncManaS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(MagicEngines.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(CastC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CastC2SPacket::new)
                .encoder(CastC2SPacket::toBytes)
                .consumerMainThread(CastC2SPacket::handle)
                .add();

        net.messageBuilder(CalculateCastC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CalculateCastC2SPacket::new)
                .encoder(CalculateCastC2SPacket::toBytes)
                .consumerMainThread(CalculateCastC2SPacket::handle)
                .add();

        net.messageBuilder(SyncManaS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncManaS2CPacket::new)
                .encoder(SyncManaS2CPacket::toBytes)
                .consumerMainThread(SyncManaS2CPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
