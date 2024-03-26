package com.josinosle.magicengines.event;


import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.mana.PlayerMana;
import com.josinosle.magicengines.mana.PlayerManaProvider;
import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.SyncManaS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerManaProvider.PLAYER_MANA).isPresent()) {
                event.addCapability(new ResourceLocation(MagicEngines.MOD_ID, "properties"), new PlayerManaProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(oldStore ->
                    event.getOriginal().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(newStore ->
                            newStore.copyFrom(oldStore)));
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerMana.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER && event.player instanceof ServerPlayer player) {
            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                if(player.getRandom().nextFloat() < 0.05f) {
                    mana.addMana(1);

                    Messages.sendToPlayer(new SyncManaS2CPacket(mana.getMana()), player);
                }
            });
        }
    }

    @SubscribeEvent
    public static void SeverPlayerFinishCastEvent(ServerPlayerFinishCastingEvent event) {
        if (event.side == LogicalSide.SERVER) {
        }
    }
}
