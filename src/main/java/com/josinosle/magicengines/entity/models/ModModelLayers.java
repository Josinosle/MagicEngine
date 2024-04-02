package com.josinosle.magicengines.entity.models;

import com.josinosle.magicengines.MagicEngines;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

/**
 * Class for intitialising model layers, derived from Block Bench's code structure
 */
public class ModModelLayers {
    public static final ModelLayerLocation ABSTRACT_SPELL_LAYER = new ModelLayerLocation(new ResourceLocation(MagicEngines.MOD_ID, "abstract_spell_projectile"), "main");

}
