package com.josinosle.magicengines.spells.spellcontent.fun;

import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.registry.SoundRegistry;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.SpellCastManaChanges;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class YeetSpell extends AbstractSpell {

    @Override
    public int triggerCast(ServerPlayer player, ArrayList<LivingEntity> entityList, Vec3 vector, double manaMultiplier, double effectValue) {
        final SpellCastManaChanges logic = new SpellCastManaChanges();
        final int manaAmount = ServerConfigs.TELEKENETIC_SLAM_REQUIRED_MANA_AMOUNT.get();

        if (logic.spellCastable(player, manaAmount) && entityList != null && !entityList.isEmpty()) {
            final Entity entity = entityList.get(0);

            // change entity delta movement
            entity.setDeltaMovement(
                    entity.getX() - player.getX() + 3,
                    entity.getY() - player.getY() + 3,
                    entity.getZ() - player.getZ() + 3);

            player.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundRegistry.YEET.get(), SoundSource.PLAYERS, Long.MAX_VALUE, 1f);
        }

        //sub mana
        logic.subMana(player, manaAmount);
        return(manaAmount);
    }
}
