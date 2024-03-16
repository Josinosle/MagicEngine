package com.josinosle.magicengines.content.spell.spellcontent.fun;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.content.spell.Spell;
import com.josinosle.magicengines.content.spell.spellcontent.SpellCastManaChanges;
import com.josinosle.magicengines.init.SoundInit;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class SpellFart extends Spell {

    public SpellFart(ServerPlayer player) {
        System.out.println("Prepare to cast fart");
        SpellCastManaChanges logic = new SpellCastManaChanges();
        if (logic.spellCastable(player,69)) {
            System.out.println("Cast fart");
            PlayerList playerList = player.getLevel().getServer().getPlayerList();

            for(ServerPlayer serverPlayer : playerList.getPlayers()) {
                serverPlayer.getLevel().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                        SoundInit.REVERB_FART.get(), SoundSource.PLAYERS, Long.MAX_VALUE, Long.MIN_VALUE);
            }
        }

    }
}
