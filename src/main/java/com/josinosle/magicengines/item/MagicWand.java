package com.josinosle.magicengines.item;

import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.CastC2SPacket;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import com.josinosle.magicengines.util.castgeometry.CastLogic;

public class MagicWand extends Item {

    private final CastLogic currentCastLogic;

    public MagicWand(Properties properties) {
        super(properties);
        currentCastLogic = new CastLogic();
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level.isClientSide) {
            //  cool down
            player.getCooldowns().addCooldown(this, 5);

            Messages.sendToServer(new CastC2SPacket());
        }
        return super.use(level, player, hand);
    }



}