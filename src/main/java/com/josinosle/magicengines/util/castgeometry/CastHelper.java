package com.josinosle.magicengines.util.castgeometry;

import com.josinosle.magicengines.content.spell.spellcontent.combat.PlayerDefence;
import com.josinosle.magicengines.content.spell.spellcontent.combat.SpellDamage;
import com.josinosle.magicengines.content.spell.spellcontent.combat.TelekeneticSlam;
import com.josinosle.magicengines.content.spell.spellcontent.fun.SpellFart;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.Objects;

public class CastHelper {

    public static void castSpell(ArrayList<String> castStack, CastVector position, ServerLevel level, ServerPlayer player){
        for (String i : castStack){
            System.out.println(i);

            // unaspected damage spell
            if (Objects.equals(i, "BBA")){
                new SpellDamage(level, player, position);
            }

            // protection spell
            if (Objects.equals(i, "CA")){
                new PlayerDefence(player);
            }

            // telekenetic slam
            if (Objects.equals(i, "CAB")){
                new TelekeneticSlam(player, position);
            }

            if(Objects.equals(i, "ACAB")) {
                new SpellFart(player, position);
            }
        }

    }
}
