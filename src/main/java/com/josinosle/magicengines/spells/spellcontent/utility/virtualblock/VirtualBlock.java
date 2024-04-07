package com.josinosle.magicengines.spells.spellcontent.utility.virtualblock;

import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.spells.AbstractSpell;
import com.josinosle.magicengines.spells.SpellCastManaChanges;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class VirtualBlock extends AbstractSpell {

    @Override
    public int triggerCast(ServerPlayer player, ArrayList<LivingEntity> entityList, Vec3 vector, double manaMultiplier, double effectValue) {

        final SpellCastManaChanges logic = new SpellCastManaChanges();
        final int manaAmount = (int) (ServerConfigs.VIRTUAL_BLOCK_REQUIRED_MANA_AMOUNT.get() * manaMultiplier);

        int blocksSpawned = spawnBlocks(vector, player);

        int totalManaCost = blocksSpawned * manaAmount;

        logic.subMana(player, totalManaCost);

        return totalManaCost;
    }

    protected int spawnBlocks (Vec3 vector, ServerPlayer player) {
        int blocksSpawned = 0;

        // create block pos variable
        int x = (int) vector.x();
        int y = (int) vector.y();
        int z = (int) vector.z();
        BlockPos blockPos = new BlockPos(x,y,z);

        player.getLevel().setBlock(blockPos, Blocks.GLOWSTONE.defaultBlockState(),2);
        blocksSpawned++;

        return blocksSpawned;
    }
}
