package com.josinosle.magicengines.spells.spellcontent.utility.virtualblock;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class VirtualBlockWall extends VirtualBlock{

    @Override
    protected int spawnBlocks (Vec3 vector, ServerPlayer player, double effectValue) {
        int blocksSpawned = 0;

        Vec3 castBeamVector = vector.subtract(player.position());

        boolean xBias = (Mth.abs((float) castBeamVector.x()) > Mth.abs((float) castBeamVector.z()));

        int xDiff = 0;
        int yDiff = 1;
        int zDiff = 0;


        if (xBias) {
            xDiff = 1;
        }
        else {
            zDiff = 1;
        }

        for (int y = (int) -effectValue; y <= effectValue; y++) {
            for (int i = (int) -effectValue; i <= effectValue; i++) {

                // create block pos variable
                int spawnX = (int) vector.x() + (i * xDiff);
                int spawnY = (int) vector.y() + (y * yDiff);
                int spawnZ = (int) vector.z() + (i * zDiff);
                BlockPos blockPos = new BlockPos(spawnX, spawnY, spawnZ);

                if (player.getLevel().getBlockState(blockPos) == Blocks.AIR.defaultBlockState()) {
                    player.getLevel().setBlock(blockPos, Blocks.AMETHYST_BLOCK.defaultBlockState(), 2);
                    blocksSpawned++;
                }
            }
        }
        return blocksSpawned;
    }


}
