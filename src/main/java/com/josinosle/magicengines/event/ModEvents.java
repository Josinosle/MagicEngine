package com.josinosle.magicengines.event;


import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.item.Grimoire;
import com.josinosle.magicengines.mana.PlayerManaProvider;
import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.SyncManaS2CPacket;
import com.josinosle.magicengines.util.cantrip.PlayerCantripProvider;
import com.josinosle.magicengines.util.casting.CastRune;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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

            if (!event.getObject().getCapability(PlayerManaProvider.PLAYER_MANA).isPresent()) {
                event.addCapability(new ResourceLocation(MagicEngines.MOD_ID, "mana_properties"),
                        new PlayerManaProvider());
            }


            if (!event.getObject().getCapability(PlayerCantripProvider.PLAYER_CANTRIP).isPresent()) {
                event.addCapability(new ResourceLocation(MagicEngines.MOD_ID, "cantrip_properties"),
                        new PlayerCantripProvider());
            }
        }
    }

    // player death capability cloners
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {

            event.getOriginal().reviveCaps();
            // set new mana capabilities to ones before death
            event.getOriginal().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(oldStore ->
                    event.getEntity().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(newStore ->
                            newStore.copyFrom(oldStore)));

            // set new cantrip capabilities to ones before death
            event.getOriginal().getCapability(PlayerCantripProvider.PLAYER_CANTRIP).ifPresent(oldStore ->
                    event.getEntity().getCapability(PlayerCantripProvider.PLAYER_CANTRIP).ifPresent(newStore ->
                            newStore.copyFrom(oldStore)));

            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER && event.player instanceof ServerPlayer player) {
            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {

                // add mana to player mana
                mana.addMana(0.002); // parameter is a balancing factor for mana regen (debugging purposes only)
                Messages.sendToPlayer(new SyncManaS2CPacket(mana.getMana(), mana.getMaxMana()), player);

            });
        }
    }

    @SubscribeEvent
    public static void ServerPlayerFinishCasting (ServerPlayerFinishCastingEvent event) {
        if(event.side == LogicalSide.SERVER && event.getPlayerCasting().getOffhandItem().getItem() instanceof Grimoire) {
            CastRune finalRune = event.getCastRunes().get(event.getCastRunes().size() - 1);
            if (finalRune.getRune() < 5) {
                int cantripIndexToOverride = finalRune.getRune() - 1; // integer index for the casting determined by the final rune on the end

                event.getPlayerCasting().getCapability(PlayerCantripProvider.PLAYER_CANTRIP).ifPresent(cantrip -> {

                    // set cantrip with cast stack and index of cast
                    cantrip.setCantrip(event.getCastRunes(), cantripIndexToOverride);
                });
            }
        }
    }
}
