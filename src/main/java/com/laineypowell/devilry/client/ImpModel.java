package com.laineypowell.devilry.client;

import com.laineypowell.devilry.entity.Demon;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public final class ImpModel extends HierarchicalModel<Demon> {
    private final ModelPart root;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public ImpModel(ModelPart root) {
        this.root = root;
        leftLeg = root.getChild("left_leg");
        rightLeg = root.getChild("right_leg");
        body = root.getChild("body");
        leftArm = root.getChild("left_arm");
        rightArm = root.getChild("right_arm");
        head = root.getChild("head");
        leftWing = root.getChild("left_wing");
        rightWing = root.getChild("right_wing");
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(Demon entity, float f, float g, float h, float i, float j) {

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-1.0F, 5.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(-1.25F, 0.0F, -2.75F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 13.0F, 1.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 57).addBox(-2.0F, 5.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 59).addBox(-2.75F, 0.0F, -2.75F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 13.0F, 1.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 0).addBox(-5.0F, -19.25F, -4.0F, 10.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(28, 37).addBox(-4.0F, -21.0F, -3.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 5.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 45).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 5.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 17).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(60, 23).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 33).addBox(-8.0F, -7.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 37).addBox(-2.0F, -2.0F, -7.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(60, 17).addBox(-1.0F, -4.0F, -8.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition right_wing = partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 6.0F, 3.0F, 0.5612F, -0.2737F, 0.1895F));

        PartDefinition left_wing = partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 6.0F, 3.0F, 0.5612F, 0.2737F, -0.1895F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}
