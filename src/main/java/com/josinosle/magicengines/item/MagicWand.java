package com.josinosle.magicengines.item;

import com.josinosle.magicengines.init.ParticleInit;
import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.CalculateCastC2SPacket;
import com.josinosle.magicengines.networking.packet.CastC2SPacket;
import com.josinosle.magicengines.util.KeyboardHelper;
import com.josinosle.magicengines.util.castgeometry.CastVector;
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
import com.josinosle.magicengines.util.castgeometry.CastLogic;

import static com.josinosle.magicengines.util.RaycastHelper.rayTrace;

public class MagicWand extends Item {

    public static byte isCasting;
    public static CastVector previousVector;

    public MagicWand(Properties properties) {
        super(properties);
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level.isClientSide) {
            //  cool down
            player.getCooldowns().addCooldown(this, 5);

            if (KeyboardHelper.isHoldingShift()) {
                System.out.println("Player cast stack cleared");
                isCasting = 0;
                Messages.sendToServer(new CalculateCastC2SPacket());
                previousVector = null;
            } else {
                // ray cast
                BlockHitResult ray = rayTrace(level, player);
                BlockPos lookPos = ray.getBlockPos().relative(ray.getDirection());

                isCasting = 1;

                //add coordinate to stack
                int x = lookPos.getX();
                int y = lookPos.getY();
                int z = lookPos.getZ();

                // send packet to server
                Messages.sendToServer(new CastC2SPacket(x, y, z));

                // spawn particle
                if(previousVector != null) {
                    CastVector tempVector2to1 = new CastVector(x - previousVector.getX(), y - previousVector.getY(), z - previousVector.getZ());

                    for (float i = 0; i < 1; i += 0.01F) {
                        //Spawn Particle
                        level.addAlwaysVisibleParticle(ParticleInit.CAST_PARTICLES.get(),
                                previousVector.getX() + tempVector2to1.getX() * i + 0.5,
                                previousVector.getY() + tempVector2to1.getY() * i + 0.5,
                                previousVector.getZ() + tempVector2to1.getZ() * i + 0.5,
                                0, 0, 0
                        );
                    }
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
                    System.out.println("Particles Spawned");
                }
            }
        }
        return super.use(level, player, hand);
    }
}