package com.josinosle.magicengines.spells.spellcontent.combat.Defence;

import net.minecraft.world.damagesource.DamageSource;

import java.util.ArrayList;

public class MagicProtection extends AbstractProtection{

    public MagicProtection () {
        super();
    }

    @Override
    protected boolean eventDamageInDamageSourceList (DamageSource source) {
        // list of possible damage sources
        ArrayList<DamageSource> tempDamageSourceList = new ArrayList<>();
        tempDamageSourceList.add(DamageSource.OUT_OF_WORLD);
        tempDamageSourceList.add(DamageSource.MAGIC);
        tempDamageSourceList.add(DamageSource.DRAGON_BREATH);

        for (DamageSource damageSource : tempDamageSourceList) {
            if (damageSource == source) {
                return true;
            }
        }
        return false;
    }
}
