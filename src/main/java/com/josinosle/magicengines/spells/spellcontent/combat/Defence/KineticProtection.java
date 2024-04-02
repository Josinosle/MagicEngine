package com.josinosle.magicengines.spells.spellcontent.combat.Defence;

import net.minecraft.world.damagesource.DamageSource;

import java.util.ArrayList;

public class KineticProtection extends AbstractProtection{
    
    public KineticProtection () {
        super();
    }

    @Override
    protected boolean eventDamageInDamageSourceList (DamageSource source) {
        ArrayList<DamageSource> tempDamageSourceList = new ArrayList<>();

        // list of possible damage sources
        tempDamageSourceList.add(DamageSource.OUT_OF_WORLD);
        tempDamageSourceList.add(DamageSource.FLY_INTO_WALL);
        tempDamageSourceList.add(DamageSource.FALL);
        tempDamageSourceList.add(DamageSource.FALLING_BLOCK);
        tempDamageSourceList.add(DamageSource.FALLING_STALACTITE);
        tempDamageSourceList.add(DamageSource.GENERIC);

        for (DamageSource damageSource : tempDamageSourceList) {
            if (damageSource == source) {
                return true;
            }
        }

        // addition conditions
        return source.isProjectile() && !source.isMagic();
    }
}
