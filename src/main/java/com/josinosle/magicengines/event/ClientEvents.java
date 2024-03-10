package com.josinosle.magicengines.event;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.content.item.MagicWand;
import com.josinosle.magicengines.util.KeyboardHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.swing.text.JTextComponent;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if(KeyboardHelper.CAST_INAIR_KEY.consumeClick()){
                if(MagicWand.isCastingInAir){
                    MagicWand.isCastingInAir = false;
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("In Air Casting Disabled"));
                }
                else{
                    MagicWand.isCastingInAir = true;
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("In Air Casting Enabled"));

                }
            }
        }

        @Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID, value = Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
        public static class ClientModBusEvents {
            @SubscribeEvent
            public static void onKeyRegister(RegisterKeyMappingsEvent event){
                event.register(KeyboardHelper.CAST_INAIR_KEY);
            }
        }
    }
}
