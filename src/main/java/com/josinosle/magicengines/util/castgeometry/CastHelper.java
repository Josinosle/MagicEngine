package com.josinosle.magicengines.util.castgeometry;

import com.josinosle.magicengines.spells.spellcontent.combat.PlayerDefence;
import com.josinosle.magicengines.spells.spellcontent.combat.SpellDamage;
import com.josinosle.magicengines.spells.spellcontent.combat.TelekeneticSlam;
import com.josinosle.magicengines.spells.spellcontent.fun.SpellFart;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.Objects;

public class CastHelper {

    public static void castSpell(ArrayList<String> castStack, CastVector position, ServerLevel level, ServerPlayer player){
        player.sendSystemMessage(Component.literal("Cast Stack").withStyle(ChatFormatting.GOLD));
        for (String i : castStack){
            System.out.println(i);

            // unaspected damage spell
            if (Objects.equals(i, "BBA")){
                new SpellDamage(level, player, position);
                player.sendSystemMessage(Component.literal("Unaspected Damage"));
            }

            // protection spell
            if (Objects.equals(i, "CA")){
                new PlayerDefence(player);
                player.sendSystemMessage(Component.literal("Protective Barrier"));
            }

            // telekenetic slam
            if (Objects.equals(i, "CAB")){
                new TelekeneticSlam(player, position);
                player.sendSystemMessage(Component.literal("Telekinetic Slam"));
            }

            if(Objects.equals(i, "ACAB")) {
                new SpellFart(level, player, position);
            }
        }

    }
}
