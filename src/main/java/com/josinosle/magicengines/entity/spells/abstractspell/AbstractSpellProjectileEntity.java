package com.josinosle.magicengines.entity.spells.abstractspell;

import com.josinosle.magicengines.registry.EntityRegistry;
import com.josinosle.magicengines.registry.ParticleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class AbstractSpellProjectileEntity extends AbstractHurtingProjectile {

    private static final double SPEED = 1;


    @Override
    protected boolean shouldBurn() {
        return false;
    }

    public AbstractSpellProjectileEntity(EntityType<? extends AbstractSpellProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractSpellProjectileEntity(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(EntityRegistry.ABSTRACT_SPELL_PROJECTILE.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
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
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    this.level.addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25, d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
                }
            }

            this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5, d2, 0.0, 0.0, 0.0);
            this.setPos(d0, d1, d2);

        } else {
            this.discard();
        }

    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ParticleRegistry.CAST_PARTICLES.get();
    }

    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        this.discard();
    }

    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (!this.level.isClientSide) {
            Entity entity = this.getOwner();
            if (!(entity instanceof Mob) || ForgeEventFactory.getMobGriefingEvent(this.level, entity)) {
                BlockPos blockpos = pResult.getBlockPos().relative(pResult.getDirection());
                if (this.level.isEmptyBlock(blockpos)) {
                    this.level.setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level, blockpos));
                }
            }
            this.discard();
        }

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

