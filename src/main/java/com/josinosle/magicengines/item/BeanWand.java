package com.josinosle.magicengines.item;

import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.EasyCastC2SPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static com.josinosle.magicengines.util.RaycastHelper.rayTrace;

public class BeanWand extends Item {
    public BeanWand(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level.isClientSide) {
            //  cool down
            player.getCooldowns().addCooldown(this, 5);

            final int range = 15;

            // ray cast
            Vec3 ray = rayTrace(level, player, range);

            //add coordinate to stack
            double x = ray.x;
            double y = ray.y;
            double z = ray.z;

            Messages.sendToServer(new EasyCastC2SPacket(x, y, z));
        }
        return super.use(level, player, hand);
    }
}
