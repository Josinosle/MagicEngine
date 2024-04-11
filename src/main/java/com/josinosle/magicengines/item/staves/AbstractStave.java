package com.josinosle.magicengines.item.staves;

import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.CastC2SPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static com.josinosle.magicengines.util.RaycastHelper.rayTrace;

public abstract class AbstractStave extends Item {

    public boolean isCastingInAir;
    public boolean isCastingCantrip;
    private final float manaEfficiency = 1;
    private boolean isCastingSpell;

    public AbstractStave(Properties properties) {
        super(properties);
    }

    /**
     * Method for usage of the wand
     */
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {

        // client side actions
        if (level.isClientSide) {

            // check if casting in air is enabled
            int range = 200;
            if (isCastingInAir) {
                range = 4;
            }

            // ray cast
            Vec3 ray = rayTrace(level, player, range);

            if (ray == null) {
                player.sendSystemMessage(Component.literal("Out of range").withStyle(ChatFormatting.DARK_RED));
                if (!isCastingSpell) {
                    return super.use(level, player, hand);
                }
            }

            // send packet to server
            assert ray != null;
            Messages.sendToServer(new CastC2SPacket(
                    ray.x(),
                    ray.y(),
                    ray.z(),
                    manaEfficiency,
                    isCastingCantrip));
        }
        return super.use(level, player, hand);
    }

    /**
     * Impose that the player cannot attack the block with the wand item
     */
    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return false;
    }

    public void spellIsBeingUsedToCastCondition () {
        isCastingSpell = true;
    }
}

