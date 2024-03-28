package com.josinosle.magicengines.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Evil ray cast class
 *
 * @author josinosle
 */
public class RaycastHelper {
    public static Vec3 rayTrace(Level world, Player player,int range) {

        // take player orientation
        float playerXRotation = player.getXRot();
        float playerYRotation = player.getYRot();

        // dont worry about this
        float f1 = Mth.cos(-playerYRotation * ((float)Math.PI / 180F) - (float)Math.PI);
        float f2 = Mth.sin(-playerYRotation * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = -Mth.cos(-playerXRotation * ((float)Math.PI / 180F));
        float f4 = Mth.sin(-playerXRotation * ((float)Math.PI / 180F));
        float f5 = f2 * f3;
        float f6 = f1 * f3;

        // player eye position stuff
        Vec3 vector3d = player.getEyePosition(1.0F);
        Vec3 vector3d1 = vector3d.add((double)f5 * range, (double)f4 * range, (double)f6 * range);

        // take world clip and get block pos
        BlockHitResult worldClip = world.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        BlockPos lookPos = worldClip.getBlockPos().relative(worldClip.getDirection());

        // suspicious truncation check to see if a vector is on a block grid
        if(lookPos.getX()!=(int)vector3d1.x && lookPos.getY()!=(int)vector3d1.y && lookPos.getY()!=(int)vector3d1.y) {
            vector3d1 = new Vec3(lookPos.getX()+0.5,lookPos.getY()+0.5,lookPos.getZ()+0.5);
        }

        // out of range check
        if ((vector3d1.subtract(vector3d)).length() > 199) {
            System.out.println((vector3d1.subtract(vector3d)).length());
            return null;
        }
        return vector3d1;
    }
}
