package com.josinosle.magicengines.util;

import com.josinosle.magicengines.spell.spellcontent.combat.SpellDamage;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class CastHelper {

    public static void castSpell(ArrayList<String> castStack, CastVector position, Level level){
        SpellDamage.castEffect(level,position);
    }
}
