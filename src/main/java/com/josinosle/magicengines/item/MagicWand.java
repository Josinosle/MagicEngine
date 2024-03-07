package com.josinosle.magicengines.item;

import com.josinosle.magicengines.api.runecast.castVector;
import com.josinosle.magicengines.util.KeyboardHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.List;
import com.josinosle.magicengines.api.runecast.castStack;

public class MagicWand extends Item {

    private castStack currentCastStack;

    public MagicWand(Properties properties) {
        super(properties);
        currentCastStack = new castStack();
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level.isClientSide) {
            if (KeyboardHelper.isHoldingShift()) {
                currentCastStack.calculateCast(level, player);
                return super.use(level, player, hand);
            } else {
                // ray cast
                BlockHitResult ray = rayTrace(level, player);
                BlockPos lookPos = ray.getBlockPos().relative(ray.getDirection());

                //  cool down
                player.getCooldowns().addCooldown(this, 5);

                //add coordinate to stack
                castVector workingVector = new castVector(lookPos.getX(), lookPos.getY(), lookPos.getZ());

                currentCastStack.setVectorComboList(workingVector,level,player);
            }
        }
        return super.use(level, player, hand);
    }


    protected static BlockHitResult rayTrace(Level world, Player player) {
        double range = 120;

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
        return world.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }
}