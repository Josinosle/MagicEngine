package com.josinosle.magicengines.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class DefenseParticles extends TextureSheetParticle {
    protected DefenseParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xd, double yd, double zd){
        super(level, x, y, z, xd, yd, zd);

        this.friction = 0.8F;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.quadSize *= 0.85F;
        this.lifetime = 20;
        this.setSpriteFromAge(spriteSet);

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;

    }

    @Override
    public void tick() {
        super.tick();
        fade();
    }

    private void fade() {
        //this.alpha = (-(1/(float) lifetime) * age + 1);

        if (age<12) {
            this.alpha = 1;
        } else if (age>12 && age<lifetime) {
            this.alpha =  (0.75F) * (float)(Math.abs(Math.cos(age  * Math.PI/3)) + (0.25F));
        }
        else {
            this.alpha = 0;
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }


        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new DefenseParticles(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
