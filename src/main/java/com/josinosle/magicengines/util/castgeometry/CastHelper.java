package com.josinosle.magicengines.util.castgeometry;

import com.josinosle.magicengines.content.spell.spellcontent.combat.PlayerDefence;
import com.josinosle.magicengines.content.spell.spellcontent.combat.SpellDamage;
import com.josinosle.magicengines.content.spell.spellcontent.combat.TelekeneticSlam;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.Objects;

public class CastHelper {

    public static void castSpell(ArrayList<String> castStack, CastVector position, ServerLevel level, ServerPlayer player){
        for (String i : castStack){
            System.out.println(i);

            // unaspected damage spell
            if (Objects.equals(i, "ABCC")){
                new SpellDamage(level,position);
            }

            // protection spell
            if (Objects.equals(i, "BCA")){
                new PlayerDefence(player);
            }

            // telekenetic slam
            if (Objects.equals(i, "CAA")){
                new TelekeneticSlam(player,position);
            }
        }

    }
}
