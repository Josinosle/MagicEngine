package com.josinosle.magicengines.spells.spellcontent;

import com.josinosle.magicengines.mana.PlayerManaProvider;
import net.minecraft.server.level.ServerPlayer;

public class SpellCastManaChanges {

    private boolean isCastable;

    public boolean spellCastable(ServerPlayer player, int thresholdMana) {
        player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
            if(mana.getMana()>=thresholdMana) {
                this.isCastable = mana.getMana() >= thresholdMana;
            }
        });
        return isCastable;
    }

    public void subMana(ServerPlayer player, int sub){
        player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> mana.subMana(sub));
    }
}
