package com.josinosle.magicengines.content.item;

import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.CastC2SPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import static com.josinosle.magicengines.util.RaycastHelper.rayTrace;

public class MagicWand extends Item {

    public static boolean isCastingInAir;

    public MagicWand(Properties properties) {
        super(properties);
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level.isClientSide) {

            //  cool down
            player.getCooldowns().addCooldown(this, 5);

            // check if casting in air is enabled
            int range = 200;
            if (isCastingInAir) {
                range = 10;
            }

            // ray cast
            BlockHitResult ray = rayTrace(level, player, range);
            BlockPos lookPos = ray.getBlockPos().relative(ray.getDirection());

            //add coordinate to stack
            int x = lookPos.getX();
            int y = lookPos.getY();
            int z = lookPos.getZ();

            // send packet to server
            Messages.sendToServer(new CastC2SPacket(x, y, z));
        }
        return super.use(level, player, hand);
    }
}
