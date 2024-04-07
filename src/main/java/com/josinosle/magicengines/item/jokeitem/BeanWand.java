package com.josinosle.magicengines.item.jokeitem;

import com.josinosle.magicengines.config.ServerConfigs;
import com.josinosle.magicengines.networking.Messages;
import com.josinosle.magicengines.networking.packet.SetDeltaMovementPacket;
import com.josinosle.magicengines.registry.ParticleRegistry;
import com.josinosle.magicengines.registry.SoundRegistry;
import com.josinosle.magicengines.spells.SpellCastManaChanges;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.josinosle.magicengines.util.RaycastHelper.rayTrace;

public class BeanWand extends Item {
    public BeanWand(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!level.isClientSide) {
            //  cool down
            player.getCooldowns().addCooldown(this, 5);

            final int range = 15;

            // ray cast
            Vec3 ray = rayTrace(level, player, range);

            triggerCast((ServerPlayer) player, ray);
        }
        return super.use(level, player, hand);
    }

    private void spawnStinkyParticles(final ServerLevel level, final Entity entity) {
        for(int i = 0; i < 360; i++) {
            if(i % 20 == 0) {
                final int count = 1;
                final double deltaX = 1;
                final double deltaY = 0.75 * Math.cos(i);
                final double deltaZ = 0.05;
                final double speed = 0.75 * Math.sin(i);

                level.getLevel().sendParticles(
                        ParticleRegistry.STINKY_PARTICLES.get(),
                        entity.getX(),
                        entity.getY() + 0.25,
                        entity.getZ(),
                        count,
                        deltaX,
                        deltaY,
                        deltaZ,
                        speed);
            }
        }
    }

    public void triggerCast(ServerPlayer player, Vec3 position) {
        final SpellCastManaChanges logic = new SpellCastManaChanges();
        final int manaAmount = (int) (ServerConfigs.FART_REQUIRED_MANA_AMOUNT.get());

        ArrayList<Entity> entityList = getTargets(position,player);

        if (logic.spellCastable(player, manaAmount)) {

            for(Entity entityInTheAreaOfSpell: entityList) {
                if(entityInTheAreaOfSpell instanceof LivingEntity livingEntity) {
                    //play fart sound
                    livingEntity.getLevel().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                            SoundRegistry.REVERB_FART.get(), SoundSource.PLAYERS, Long.MAX_VALUE, Long.MIN_VALUE);

                    spawnStinkyParticles(player.getLevel(), livingEntity);

                    //set nausea effect for 400 ticks aka 20 seconds
                    final int duration = 400;
                    final MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.CONFUSION, duration);
                    livingEntity.addEffect(mobEffectInstance);

                    //add jump effect
                    if(livingEntity instanceof Player playerInAreaOfSpell) {
                        Messages.sendToPlayer(new SetDeltaMovementPacket(), (ServerPlayer) player.getLevel().getPlayerByUUID(playerInAreaOfSpell.getUUID()));
                    } else {
                        livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0, 1, 0));
                    }


                }

            }

            logic.subMana(player, manaAmount);
        }
    }
    
    public ArrayList<Entity> getTargets (Vec3 vector, ServerPlayer player) {
        // define entities
        ArrayList<Entity> entities = new ArrayList<>();
        
        // define a bounding box
        AABB boundBox = new AABB(vector.x() - 3, vector.y() - 3, vector.z() - 3, vector.x() + 3, vector.y() + 3, vector.z() + 3);

        // add entities in a bounding box to working list
        List<Entity> entToDamage = player.getLevel().getEntities(null, boundBox);
        for(Entity entity : entToDamage) {
            if (entity.getId() != player.getId()) {
                entities.add(entity);
            }
        }
        return entities;
    }
}
