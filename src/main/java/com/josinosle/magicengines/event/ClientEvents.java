package com.josinosle.magicengines.event;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.item.MagicWand;
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
        public static void onKeyInput(InputEvent.Key event){
            final LocalPlayer localPlayer = Minecraft.getInstance().player;
            if(KeyboardHelper.CAST_INAIR_KEY.consumeClick() && localPlayer != null){
                if(MagicWand.isCastingInAir){
                    MagicWand.isCastingInAir = false;
                    localPlayer.sendSystemMessage(Component.literal("In Air Casting Disabled"));
                }
                else{
                    MagicWand.isCastingInAir = true;
                    localPlayer.sendSystemMessage(Component.literal("In Air Casting Enabled"));

                }
            }
            if(KeyboardHelper.CASTCALC_KEY.consumeClick() && localPlayer != null){
                Messages.sendToServer(new CalculateCastC2SPacket());
            }
        }
    }
}
