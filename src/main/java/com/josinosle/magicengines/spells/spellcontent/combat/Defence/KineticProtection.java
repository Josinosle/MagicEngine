package com.josinosle.magicengines.spells.spellcontent.combat.Defence;

import net.minecraft.world.damagesource.DamageSource;


public class KineticProtection extends AbstractProtection{
    
    public KineticProtection () {
        super();
    }

    @Override
    protected boolean eventDamageInDamageSourceList (DamageSource source) {

        // list of possible damage sources
        if (source.msgId.equals("outOfWorld")) return true;
        if (source.msgId.equals("flyIntoWall")) return true;
        if (source.msgId.equals("fall")) return true;
        if (source.msgId.equals("fallingBlock")) return true;
        if (source.msgId.equals("fallingStalectite")) return true;
        if (source.msgId.equals("generic")) return true;
        if (source.msgId.equals("player")) return true;
        if (source.msgId.equals("mob")) return true;

        // special conditions
        return source.isProjectile() && !source.isMagic();
    }
}
