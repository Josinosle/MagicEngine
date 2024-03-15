package com.josinosle.magicengines.content.spell.spellcontent.fun;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.content.spell.Spell;
import com.josinosle.magicengines.content.spell.spellcontent.SpellCastManaChanges;
import com.josinosle.magicengines.init.SoundInit;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import com.josinosle.magicengines.util.castgeometry.NetworkCastLogicHandling;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class SpellFart extends Spell {

    public SpellFart(ServerPlayer player) {
        SpellCastManaChanges logic = new SpellCastManaChanges();
        if (logic.spellCastable(player,69)) {

            for(ServerPlayer serverPlayer : NetworkCastLogicHandling.getWorldPlayers()) {
                serverPlayer.getLevel().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                        SoundInit.REVERB_FART.get(), SoundSource.PLAYERS, Long.MAX_VALUE, Long.MAX_VALUE);
            }
        }

    }
}
