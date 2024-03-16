package com.josinosle.magicengines.content.item;

import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.EasyCastC2SPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BeanWand extends Item {
    public BeanWand(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level.isClientSide) {
            System.out.println("Bean wand used");
            Messages.sendToServer(new EasyCastC2SPacket());
        }
        return super.use(level, player, hand);
    }
}
