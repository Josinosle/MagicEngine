package com.josinosle.magicengines.spells;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;

public abstract class AbstractSpell {
    private int spellEffectModulus;

    public AbstractSpell(){
    }

    // trigger effect
    public abstract int triggerCast(ServerPlayer player, ArrayList<LivingEntity> entityList, double manaMultiplier, double effectValue);
}
