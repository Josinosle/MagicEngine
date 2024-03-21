package com.josinosle.magicengines.spells;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;

public abstract class AbstractSpell {
    private int spellEffectModulus;

    public AbstractSpell(){
    }

    public void triggerCast(){

    }

    // trigger effect
    public abstract void triggerCast(ServerPlayer player, Entity entity, ArrayList<Entity> entityList);
}
