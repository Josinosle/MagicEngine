package com.josinosle.magicengines.item;

import com.josinosle.magicengines.api.castgeometry.CastVector;
import com.josinosle.magicengines.util.KeyboardHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import com.josinosle.magicengines.api.castgeometry.CastStack;

import static com.josinosle.magicengines.util.RaycastHelper.rayTrace;

public class MagicWand extends Item {

    private final CastStack currentCastStack;

    public MagicWand(Properties properties) {
        super(properties);
        currentCastStack = new CastStack();
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
                CastVector workingVector = new CastVector(lookPos.getX(), lookPos.getY(), lookPos.getZ());

                spawnParticles(workingVector, level, player);
                currentCastStack.setVectorComboList(workingVector,level,player);
            }
        }
        return super.use(level, player, hand);
    }
    private void spawnParticles(CastVector vector, Level level, Player player) {

        for(float i = 0; i <= 360; i+= 72){
            //Spawn Particle
            level.addAlwaysVisibleParticle(ParticleTypes.SOUL_FIRE_FLAME,
                    vector.getX() + 0.5,
                    vector.getY() + 0.7,
                    vector.getZ() + 0.5,
                    Math.cos(i) * 0.1, 0, Math.sin(i) * 0.1
            );
        }
        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
    }



}