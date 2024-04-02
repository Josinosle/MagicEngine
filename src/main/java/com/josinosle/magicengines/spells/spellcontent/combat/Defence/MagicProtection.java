package com.josinosle.magicengines.spells.spellcontent.combat.Defence;

import net.minecraft.world.damagesource.DamageSource;


public class MagicProtection extends AbstractProtection{

    public MagicProtection () {
        super();
    }

    @Override
    protected boolean eventDamageInDamageSourceList (DamageSource source) {

        // list of possible damage sources
        if (source.msgId.equals("magic")) return true;
        if (source.msgId.equals("dragonBreath")) return true;

        return false;
    }
}
