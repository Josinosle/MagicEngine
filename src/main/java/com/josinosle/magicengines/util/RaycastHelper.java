package com.josinosle.magicengines.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class RaycastHelper {
    public static Vec3 rayTrace(Level world, Player player,int range) {
        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vector3d = player.getEyePosition(1.0F);
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vector3d1 = vector3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);

        BlockHitResult worldClip = world.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        BlockPos lookPos = worldClip.getBlockPos().relative(worldClip.getDirection());

        if(lookPos.getX()!=(int)vector3d1.x && lookPos.getY()!=(int)vector3d1.y && lookPos.getY()!=(int)vector3d1.y) {
            vector3d1 = new Vec3(lookPos.getX()+0.5,lookPos.getY()+0.5,lookPos.getZ()+0.5);
        }

        if ((vector3d1.subtract(vector3d)).length() > 199) {
            System.out.println((vector3d1.subtract(vector3d)).length());
            return null;
        }
        return vector3d1;
    }
}
