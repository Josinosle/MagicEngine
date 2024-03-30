package com.josinosle.magicengines.event;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.item.staves.AbstractStave;
import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.CalculateCastC2SPacket;
import com.josinosle.magicengines.util.KeyboardHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onCastInAirKeyInput(InputEvent event){
            final LocalPlayer localPlayer = Minecraft.getInstance().player;

            // casting in air key on/off key logic
            if(KeyboardHelper.CAST_INAIR_KEY.consumeClick() && localPlayer != null
            ){

                // check if main hand or offhand contain a wand (priority on main hand)
                AbstractStave equippedStave;
                if (localPlayer.getMainHandItem().getItem() instanceof AbstractStave) {
                    equippedStave = (AbstractStave) localPlayer.getMainHandItem().getItem();
                }
                else if (localPlayer.getOffhandItem().getItem() instanceof AbstractStave) {
                    equippedStave = (AbstractStave) localPlayer.getOffhandItem().getItem();
                }
                else {
                    return;
                }

                // switch bool to opposite
                equippedStave.isCastingInAir = !equippedStave.isCastingInAir;
                localPlayer.sendSystemMessage(Component.literal("Casting in air: " + equippedStave.isCastingInAir));
            }

            // casting cantrip on/off key logic
            if(KeyboardHelper.CASTCANTRIP_KEY.consumeClick() && localPlayer != null
            ){

                // check if main hand or offhand contain a wand (priority on main hand)
                AbstractStave equippedStave;
                if (localPlayer.getMainHandItem().getItem() instanceof AbstractStave) {
                    equippedStave = (AbstractStave) localPlayer.getMainHandItem().getItem();
                }
                else if (localPlayer.getOffhandItem().getItem() instanceof AbstractStave) {
                    equippedStave = (AbstractStave) localPlayer.getOffhandItem().getItem();
                }
                else {
                    return;
                }

                // switch bool to opposite
                equippedStave.isCastingCantrip = !equippedStave.isCastingCantrip;
                localPlayer.sendSystemMessage(Component.literal("Cantrip casting: " + equippedStave.isCastingCantrip));
            }

            // cast calc key logic
            if(KeyboardHelper.CASTCALC_KEY.consumeClick() && localPlayer != null){
                Messages.sendToServer(new CalculateCastC2SPacket());
            }
        }
    }
}
