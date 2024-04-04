package com.josinosle.magicengines.entity.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class AbstractSpellModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart BeamSegment;

    public AbstractSpellModel(ModelPart root) {
        this.BeamSegment = root.getChild("BeamSegment");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition BeamSegment = partdefinition.addOrReplaceChild("BeamSegment", CubeListBuilder.create().texOffs(0, 2).addBox(-8.0F, -5.0F, -5.0F, 16.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-8.0F, -3.0F, -3.0F, 16.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-8.0F, -2.0F, -2.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        BeamSegment.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}