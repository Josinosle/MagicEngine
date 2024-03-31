package com.josinosle.magicengines.spells.spellcontent.combat.Defence;

import net.minecraft.world.damagesource.DamageSource;

import java.util.ArrayList;

public class ElementalProtection extends AbstractProtection{

    public ElementalProtection () {
        super();
    }

    @Override
    protected boolean eventDamageInDamageSourceList (DamageSource source) {
        // list of possible damage sources
        ArrayList<DamageSource> tempDamageSourceList = new ArrayList<>();
        tempDamageSourceList.add(DamageSource.OUT_OF_WORLD);
        tempDamageSourceList.add(DamageSource.FREEZE);
        tempDamageSourceList.add(DamageSource.ON_FIRE);
        tempDamageSourceList.add(DamageSource.LIGHTNING_BOLT);
        tempDamageSourceList.add(DamageSource.IN_FIRE);

        for (DamageSource damageSource : tempDamageSourceList) {
            if (damageSource == source) {
                return true;
            }
        }
        return false;
    }
}