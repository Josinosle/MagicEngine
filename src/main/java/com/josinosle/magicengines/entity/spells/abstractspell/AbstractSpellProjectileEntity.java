package com.josinosle.magicengines.entity.spells.abstractspell;

import com.josinosle.magicengines.registry.EntityRegistry;
import com.josinosle.magicengines.registry.ParticleRegistry;
import com.josinosle.magicengines.util.casting.CastHelper;
import com.josinosle.magicengines.util.casting.CastRune;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AbstractSpellProjectileEntity extends AbstractHurtingProjectile {

    private static final double SPEED = 1;

    private ArrayList<CastRune> castingStack;
    private double manaEfficiency;
    private ServerPlayer player;

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    public AbstractSpellProjectileEntity(EntityType<? extends AbstractSpellProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractSpellProjectileEntity(Level pLevel, LivingEntity pShooter, ArrayList<CastRune> pCastingStack, double pManaEfficiency, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(EntityRegistry.ABSTRACT_SPELL_PROJECTILE.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
        castingStack = pCastingStack;
        manaEfficiency = pManaEfficiency;
        player = (ServerPlayer) pShooter;
    }

    public AbstractSpellProjectileEntity(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(EntityRegistry.ABSTRACT_SPELL_PROJECTILE.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 4.0;
        if (Double.isNaN(d0)) {
            d0 = 4.0;
        }

        d0 *= 64.0;
        return pDistance < d0 * d0;
    }

    @Override
    public void tick() {
        if (firstTick) {
            this.setPos(
                    this.getX(),
                    this.getEyeHeight()+this.getY(),
                    this.getZ()
            );

        }
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
            ProjectileUtil.rotateTowardsMovement(this, 0.1F);

            // functions for adding particles during entity movement
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    this.level.addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25, d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
                }
            }

            this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5, d2, 0.0, 0.0, 0.0);
            this.setPos(d0, d1, d2);

            // check if entity is still moving (if not discard it)
            if (Mth.abs((float) this.getDeltaMovement().lengthSqr())<0.1) {
                this.discard();
            }

        } else {
            this.discard();
        }

    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ParticleRegistry.CAST_PARTICLES.get();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        if (!this.level.isClientSide) {
            Vec3 vector = pResult.getLocation(); // get location of effected entity

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
            CastHelper.cast(tempCastStack, vector, player, manaEfficiency);
        }
        this.discard();
    }

    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        if (!this.level.isClientSide) {
            Vec3 vector = pResult.getLocation(); // get location of effected entity

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
            CastHelper.cast(tempCastStack, vector, player, manaEfficiency);
        }
        this.discard();

    }

    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            this.discard();
        }
    }

    public boolean isPickable() {
        return false;
    }

    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        return false;
    }
}

