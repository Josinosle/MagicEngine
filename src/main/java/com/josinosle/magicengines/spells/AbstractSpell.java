package com.josinosle.magicengines.spells;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;

public abstract class AbstractSpell {
    private int spellEffectModulus;

    public AbstractSpell(){
    }

    // trigger effect
    public abstract int triggerCast(ServerPlayer player, ArrayList<Entity> entityList,double manaMultiplier, double effectValue);
}
