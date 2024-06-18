package com.josinosle.magicengines.spells.spellcontent;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.SpellCastManaChanges;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class InvalidRunePunishment extends AbstractSpell {
    public InvalidRunePunishment() {
        super();
    }

    @Override
    public int triggerCast(ServerPlayer player, ArrayList<LivingEntity> entityList, Vec3 vector, double manaMultiplier, double effectValue) {
        final SpellCastManaChanges logic = new SpellCastManaChanges();

        int manaAmount = ServerConfigs.PUNISHMENT_MANA_COST.get();

        //sub mana
        logic.subMana(player, manaAmount);
        return manaAmount;
    }
}
