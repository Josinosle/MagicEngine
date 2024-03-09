package com.josinosle.magicengines.spell.spellcontent.combat;

import com.josinosle.magicengines.spell.Spell;
import com.josinosle.magicengines.util.castgeometry.CastVector;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import static net.minecraftforge.fml.loading.FMLEnvironment.dist;

public class SpellDamage extends Spell {

    private static int effectModulus = 10;
    
    public void cast(Level level, BlockPos vector){
        level.getBlockEntity(vector);
    }

    public static void castEffect(Level level, CastVector vector){
        if(dist.isDedicatedServer()) {
            AABB boundBox = new AABB(vector.getX() - 5, vector.getY() - 5, vector.getZ() - 5, vector.getX() + 5, vector.getY() + 5, vector.getZ() + 5);
            List<Entity> entToDamage = level.getEntities(null, boundBox);
            for (Entity i : entToDamage) {
                i.hurt(DamageSource.MAGIC, effectModulus);
                System.out.println("Entity Damaged!");
            }
        }
    }
}
