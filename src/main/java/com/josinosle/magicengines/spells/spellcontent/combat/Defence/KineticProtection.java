package com.josinosle.magicengines.spells.spellcontent.combat.Defence;

import net.minecraft.world.damagesource.DamageSource;

import java.util.ArrayList;

public class KineticProtection extends AbstractProtection{
    
    public KineticProtection () {
        super();
    }

    @Override
    protected ArrayList<DamageSource> damageSourcesDefendedAgainst () {
        ArrayList<DamageSource> tempDamageSourceList = new ArrayList<>();
        tempDamageSourceList.add(DamageSource.OUT_OF_WORLD);
        tempDamageSourceList.add(DamageSource.FLY_INTO_WALL);
        tempDamageSourceList.add(DamageSource.FALL);
        tempDamageSourceList.add(DamageSource.FALLING_BLOCK);
        tempDamageSourceList.add(DamageSource.FALLING_STALACTITE);
        tempDamageSourceList.add(DamageSource.GENERIC);
        return tempDamageSourceList;
    }
}