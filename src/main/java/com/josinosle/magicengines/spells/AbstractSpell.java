package com.josinosle.magicengines.spells;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public abstract class AbstractSpell {
    public AbstractSpell(){
    }

    // trigger effect
    public abstract int triggerCast(ServerPlayer player, ArrayList<LivingEntity> entityList, Vec3 vector, double manaMultiplier, double effectValue);
}
