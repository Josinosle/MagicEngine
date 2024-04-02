package com.josinosle.magicengines.spells.spellcontent.combat.Defence;

import net.minecraft.world.damagesource.DamageSource;


public class ElementalProtection extends AbstractProtection{

    public ElementalProtection () {
        super();
    }

    @Override
    protected boolean eventDamageInDamageSourceList (DamageSource source) {

        // list of possible damage sources
        if (source.msgId.equals("freeze")) return true;
        if (source.msgId.equals("onFire")) return true;
        if (source.msgId.equals("lightningBolt")) return true;
        if (source.msgId.equals("inFire")) return true;

        return false;
    }
}