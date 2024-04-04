package com.josinosle.magicengines.entity.spells;

import com.josinosle.magicengines.registry.EntityRegistry;
import com.josinosle.magicengines.registry.SoundRegistry;
import com.josinosle.magicengines.util.casting.CastHelper;
import com.josinosle.magicengines.util.casting.CastRune;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AbstractSpellEntity extends Projectile {

    private ArrayList<CastRune> castingStack;
    private double manaEfficiency;
    private ServerPlayer player;

    public AbstractSpellEntity(EntityType<? extends AbstractSpellEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractSpellEntity(Level pLevel, LivingEntity pShooter, ArrayList<CastRune> pCastingStack, double pManaEfficiency) {
        this(EntityRegistry.ABSTRACT_SPELL.get(),pLevel);

        // parameters for entity trajectory management
        this.setOwner(pShooter);
        this.setRot(pShooter.getYRot(), pShooter.getXRot());
        this.moveTo(pShooter.getX(), pShooter.getEyeY(), pShooter.getZ(), this.getYRot(), this.getXRot());
        this.reapplyPosition();

        // parameters set for casting
        castingStack = pCastingStack;
        manaEfficiency = pManaEfficiency;
        player = (ServerPlayer) pShooter;

        // sound effects
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegistry.BEAM_BOOM.get(), SoundSource.PLAYERS, 1.0F, 0.8F);
    }

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.isRemoved())) {
            super.tick();

            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;

            // functions for adding particles during entity movement
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    this.level.addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25, d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
                }
            }
            this.setPos(d0, d1, d2);

            // check if entity is still moving (if not discard it)
            if (Mth.abs((float) this.getDeltaMovement().lengthSqr())<0.05) {
                this.discard();
            }

        } else {
            this.discard();
        }

    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {

        if (!this.level.isClientSide) {
            Vec3 vector = pResult.getLocation(); // get location of effected entity
            castSpell(vector);
        }
        this.discard();
    }

    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        if (!this.level.isClientSide) {
            Vec3 vector = pResult.getLocation(); // get location of effected entity
            castSpell(vector);
        }
        this.discard();
    }

    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            this.discard();
        }
    }

    private void castSpell (Vec3 pVector) {
        ArrayList<CastRune> tempCastStack = new ArrayList<>();

        if (castingStack == null) {
            return;
        }

        for (CastRune rune : castingStack) {

            // replace cast stack ray values with a special case rune
            if (rune.getRune() == 3) {
                CastRune tempRune = new CastRune(
                        rune.getCastMagnitude(),
                        -1);
                tempCastStack.add(
                        castingStack.indexOf(rune),
                        tempRune);
                continue;
            }

            // default condition
            tempCastStack.add(
                    castingStack.indexOf(rune),
                    rune
            );
        }
        CastHelper.cast(tempCastStack, pVector, player, manaEfficiency);
    }

    @Override
    protected void defineSynchedData() {}

    public boolean isPickable() {
        return false;
    }
}
