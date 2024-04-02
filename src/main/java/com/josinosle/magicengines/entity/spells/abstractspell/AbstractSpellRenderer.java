package com.josinosle.magicengines.entity.spells.abstractspell;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.entity.models.AbstractSpellModel;
import com.josinosle.magicengines.entity.models.ModModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class AbstractSpellRenderer extends EntityRenderer<AbstractSpellProjectileEntity> {

    // values to change on renderer inheritance
    private static AbstractSpellModel MODEL;
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(MagicEngines.MOD_ID,"textures/entities/abstract_spell_projectile.png");
    private final ModelLayerLocation MODEL_LAYER_LOCATION = ModModelLayers.ABSTRACT_SPELL_LAYER;


    protected static final RenderType RENDER_TYPE;

    public AbstractSpellRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        //this.model = new AbstractSpellModel(pContext.bakeLayer(ModelLayers.TRIDENT));
        this.MODEL = new AbstractSpellModel(pContext.bakeLayer(MODEL_LAYER_LOCATION));
    }

    protected int getBlockLightLevel(AbstractSpellProjectileEntity pEntity, BlockPos pPos) {
        return 15;
    }

    public void render(AbstractSpellProjectileEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) + 90.0F));
        VertexConsumer $$6 = ItemRenderer.getFoilBufferDirect(pBuffer, this.MODEL.renderType(this.getTextureLocation(pEntity)), false, false);
        this.MODEL.renderToBuffer(pMatrixStack, $$6, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    protected static void vertex(VertexConsumer p_114090_, Matrix4f p_114091_, Matrix3f p_114092_, int p_114093_, float p_114094_, int p_114095_, int p_114096_, int p_114097_) {
        p_114090_.vertex(p_114091_, p_114094_ - 0.5F, (float)p_114095_ - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)p_114096_, (float)p_114097_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_114093_).normal(p_114092_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    public @NotNull ResourceLocation getTextureLocation(AbstractSpellProjectileEntity pEntity) {
        return TEXTURE_LOCATION;
    }

    static {
        RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
    }
}