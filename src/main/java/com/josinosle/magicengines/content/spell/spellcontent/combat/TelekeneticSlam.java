package com.josinosle.magicengines.content.spell.spellcontent.combat;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.init.ParticleInit;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MagicEngines.MOD_ID)
public class TelekeneticSlam {

    public TelekeneticSlam(ServerPlayer player, CastVector vector) {
        AABB boundBox = new AABB(vector.getX() - 5, vector.getY() - 5, vector.getZ() - 5, vector.getX() + 5, vector.getY() + 5, vector.getZ() + 5);
        List<Entity> entToDamage = player.getLevel().getEntities(null, boundBox);
        for (Entity i : entToDamage) {
            // change entity delta movement
            i.setDeltaMovement(
                    i.getX()-player.getX(),
                    i.getY()-player.getY()+0.2,
                    i.getZ()-player.getZ());


                // send spawn particle
            player.getLevel().sendParticles(
                    ParticleTypes.EXPLOSION,
                    i.getX(),
                    i.getY()+1,
                    i.getZ(),
                    1,
                    0,
                    0.05,
                    0,
                    0);
        }
    }
}
