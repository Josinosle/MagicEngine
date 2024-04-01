package com.josinosle.magicengines.registry;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.entity.spells.abstractspell.AbstractSpellProjectileEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MagicEngines.MOD_ID);

    public static final RegistryObject<EntityType> ABSTRACT_SPELL_PROJECTILE = ENTITY_TYPES.register(
            "abstract_spell_projectile",
            () -> EntityType.Builder.<AbstractSpellProjectileEntity>of(AbstractSpellProjectileEntity::new,
                    MobCategory.MISC)
                    .sized(0.5f,0.5f).build("abstract_spell_projectile"));


}
