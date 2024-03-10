package com.josinosle.magicengines.event;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.content.item.MagicWand;
import com.josinosle.magicengines.util.KeyboardHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
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
        }

        @Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID, value = Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
        public enum ClientModBusEvents {
            ;

            @SubscribeEvent
            public static void onKeyRegister(RegisterKeyMappingsEvent event){
                event.register(KeyboardHelper.CAST_INAIR_KEY);
            }
        }
    }
}
