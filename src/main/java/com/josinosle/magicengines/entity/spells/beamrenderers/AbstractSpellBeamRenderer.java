package com.josinosle.magicengines.entity.spells.beamrenderers;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.entity.models.AbstractSpellModel;
import com.josinosle.magicengines.entity.models.ModModelLayers;
import com.josinosle.magicengines.entity.spells.AbstractSpellEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class AbstractSpellBeamRenderer extends EntityRenderer<AbstractSpellEntity> {

    // values to change on renderer inheritance
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(MagicEngines.MOD_ID,"textures/entities/abstract_spell_projectile.png");
    private static AbstractSpellModel MODEL;
    private final ModelLayerLocation MODEL_LAYER_LOCATION = ModModelLayers.ABSTRACT_SPELL_LAYER;

    private Vec3 startVec;

    protected static final RenderType RENDER_TYPE;

    public AbstractSpellBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        MODEL = new AbstractSpellModel(pContext.bakeLayer(MODEL_LAYER_LOCATION));
    }

    protected int getBlockLightLevel(AbstractSpellEntity pEntity, BlockPos pPos) {
        return 15;
    }


    public void render(AbstractSpellEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        if (pEntity.tickCount == 3) startVec = pEntity.getPosition(pPartialTicks);
        if (startVec == null || pEntity.tickCount < 3) return;

        /*
        float $$8 = (float) pEntity.level.getGameTime() + pPartialTicks;
        float $$9 = $$8 * 0.5F % 1.0F;
        float $$10 = pEntity.getEyeHeight();
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0, (double) $$10, 0.0);
        Vec3 $$11 = this.startVec;
        Vec3 $$12 = pEntity.getPosition(pPartialTicks);
        Vec3 $$13 = $$11.subtract($$12);
        float $$14 = (float) ($$13.length() + 1.0);
        $$13 = $$13.normalize();
        float $$15 = (float) Math.acos($$13.y);
        float $$16 = (float) Math.atan2($$13.z, $$13.x);
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((1.5707964F - $$16) * 57.295776F));
        pMatrixStack.mulPose(Vector3f.XP.rotationDegrees($$15 * 57.295776F));
        float $$18 = $$8 * 0.05F * -1.5F;
        float $$23 = 0.2F;
        float $$24 = 0.282F;
        float $$25 = Mth.cos($$18 + 2.3561945F) * 0.282F;
        float $$26 = Mth.sin($$18 + 2.3561945F) * 0.282F;
        float $$27 = Mth.cos($$18 + 0.7853982F) * 0.282F;
        float $$28 = Mth.sin($$18 + 0.7853982F) * 0.282F;
        float $$29 = Mth.cos($$18 + 3.926991F) * 0.282F;
        float $$30 = Mth.sin($$18 + 3.926991F) * 0.282F;
        float $$31 = Mth.cos($$18 + 5.4977875F) * 0.282F;
        float $$32 = Mth.sin($$18 + 5.4977875F) * 0.282F;
        float $$33 = Mth.cos($$18 + 3.1415927F) * 0.2F;
        float $$34 = Mth.sin($$18 + 3.1415927F) * 0.2F;
        float $$35 = Mth.cos($$18 + 0.0F) * 0.2F;
        float $$36 = Mth.sin($$18 + 0.0F) * 0.2F;
        float $$37 = Mth.cos($$18 + 1.5707964F) * 0.2F;
        float $$38 = Mth.sin($$18 + 1.5707964F) * 0.2F;
        float $$39 = Mth.cos($$18 + 4.712389F) * 0.2F;
        float $$40 = Mth.sin($$18 + 4.712389F) * 0.2F;
        float $$42 = 0.0F;
        float $$43 = 0.4999F;
        float $$44 = -1.0F + $$9;
        float $$45 = $$14 * 2.5F + $$44;
        VertexConsumer $$46 = pBuffer.getBuffer(RENDER_TYPE);
        PoseStack.Pose $$47 = pMatrixStack.last();
        Matrix4f $$48 = $$47.pose();
        Matrix3f $$49 = $$47.normal();
        vertex($$46, $$48, $$49, $$33, $$14, $$34, 0.4999F, $$45);
        vertex($$46, $$48, $$49, $$33, 0.0F, $$34, 0.4999F, $$44);
        vertex($$46, $$48, $$49, $$35, 0.0F, $$36, 0.0F, $$44);
        vertex($$46, $$48, $$49, $$35, $$14, $$36, 0.0F, $$45);
        vertex($$46, $$48, $$49, $$37, $$14, $$38, 0.4999F, $$45);
        vertex($$46, $$48, $$49, $$37, 0.0F, $$38, 0.4999F, $$44);
        vertex($$46, $$48, $$49, $$39, 0.0F, $$40, 0.0F, $$44);
        vertex($$46, $$48, $$49, $$39, $$14, $$40, 0.0F, $$45);
        float $$50 = 0.0F;
        if (pEntity.tickCount % 2 == 0) {
            $$50 = 0.5F;
        }

        vertex($$46, $$48, $$49, $$25, $$14, $$26, 0.5F, $$50 + 0.5F);
        vertex($$46, $$48, $$49, $$27, $$14, $$28, 1.0F, $$50 + 0.5F);
        vertex($$46, $$48, $$49, $$31, $$14, $$32, 1.0F, $$50);
        vertex($$46, $$48, $$49, $$29, $$14, $$30, 0.5F, $$50);
 */

        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(pBuffer, MODEL.renderType(this.getTextureLocation(pEntity)), false, false);
        double renderVectorLengh = ( (pEntity.getPosition(pPartialTicks)).subtract(startVec) ).length();

        // render beam sections
        for (int i = 0; i <= renderVectorLengh; i++) {
            pMatrixStack.pushPose();
            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
            pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) + 90.0F));
            pMatrixStack.translate(0,renderVectorLengh-i,0);
            MODEL.renderToBuffer(pMatrixStack, vertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            pMatrixStack.popPose();
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    public @NotNull ResourceLocation getTextureLocation(AbstractSpellEntity pEntity) {
        return TEXTURE_LOCATION;
    }

    static {
        RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
    }
}