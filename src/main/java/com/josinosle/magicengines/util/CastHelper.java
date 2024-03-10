package com.josinosle.magicengines.util;

import com.josinosle.magicengines.content.spell.spellcontent.combat.PlayerDefence;
import com.josinosle.magicengines.content.spell.spellcontent.combat.SpellDamage;
import com.josinosle.magicengines.util.castgeometry.CastLogic;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.Objects;

public class CastHelper {

    public static void castSpell(ArrayList<String> castStack, CastVector position, ServerLevel level, ServerPlayer player){
        for (String i : CastLogic.getCastingStack()){
            System.out.println(i);

            // unaspected damage spell
            if (Objects.equals(i, "ABCC")){
                new SpellDamage(level,position);
            }

            // protection spell
            if (Objects.equals(i, "BDA")){
                new PlayerDefence(player);
            }
        }

    }
}
