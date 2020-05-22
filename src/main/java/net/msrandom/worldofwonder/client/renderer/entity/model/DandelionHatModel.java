package net.msrandom.worldofwonder.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DandelionHatModel extends BipedModel<LivingEntity> {
    public static final DandelionHatModel INSTANCE = new DandelionHatModel();

    public ModelRenderer hat;
    public ModelRenderer fluff1;
    public ModelRenderer fluff2;
    public ModelRenderer fluff3;
    public ModelRenderer fluff4;
    public ModelRenderer fluff5;
    public ModelRenderer fluff6;
    public ModelRenderer fluff7;
    public ModelRenderer fluff8;

    public DandelionHatModel() {
        super(0);
        this.textureWidth = 150;
        this.textureHeight = 128;
        this.fluff7 = new ModelRenderer(this, 64, 99);
        this.fluff7.setRotationPoint(-3.0F, -8.5F, -3.0F);
        this.fluff7.addBox(-4.5F, 0.0F, -10.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff7, -0.4363323129985824F, 0.7853981633974483F, 0.0F);
        this.fluff8 = new ModelRenderer(this, 64, 111);
        this.fluff8.setRotationPoint(3.0F, -8.5F, -3.0F);
        this.fluff8.addBox(-4.5F, 0.0F, -10.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff8, -0.4363323129985824F, -0.7853981633974483F, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.setRotationPoint(-1.899999976158142F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 32, 48);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.hat = new ModelRenderer(this, 94, 64);
        this.hat.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hat.addBox(-5.0F, -9.5F, -5.0F, 10, 4, 10, 0.0F);
        this.fluff4 = new ModelRenderer(this, 64, 88);
        this.fluff4.mirror = true;
        this.fluff4.setRotationPoint(5.0F, -8.5F, 0.0F);
        this.fluff4.addBox(0.0F, 0.0F, -4.5F, 10, 0, 9, 0.0F);
        this.setRotateAngle(fluff4, 0.0F, 0.0F, -0.4363323129985824F);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.fluff1 = new ModelRenderer(this, 64, 64);
        this.fluff1.setRotationPoint(0.0F, -8.5F, 5.0F);
        this.fluff1.addBox(-4.5F, 0.0F, 0.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff1, 0.4363323129985824F, 0.0F, 0.0F);
        this.fluff3 = new ModelRenderer(this, 64, 76);
        this.fluff3.setRotationPoint(0.0F, -8.5F, -5.0F);
        this.fluff3.addBox(-4.5F, 0.0F, -10.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff3, -0.4363323129985824F, 0.0F, 0.0F);
        this.fluff2 = new ModelRenderer(this, 64, 88);
        this.fluff2.setRotationPoint(-5.0F, -8.5F, 0.0F);
        this.fluff2.addBox(-10.0F, 0.0F, -4.5F, 10, 0, 9, 0.0F);
        this.setRotateAngle(fluff2, 0.0F, 0.0F, 0.4363323129985824F);
        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.setRotationPoint(1.899999976158142F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.fluff5 = new ModelRenderer(this, 84, 111);
        this.fluff5.setRotationPoint(3.0F, -8.5F, 3.0F);
        this.fluff5.addBox(-4.5F, 0.0F, 0.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff5, 0.4363323129985824F, 0.7853981633974483F, 0.0F);
        this.fluff6 = new ModelRenderer(this, 84, 99);
        this.fluff6.setRotationPoint(-3.0F, -8.5F, 3.0F);
        this.fluff6.addBox(-4.5F, 0.0F, 0.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff6, 0.4363323129985824F, -0.7853981633974483F, 0.0F);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.hat.addChild(this.fluff7);
        this.hat.addChild(this.fluff8);
        this.bipedHead.addChild(this.hat);
        this.hat.addChild(this.fluff4);
        this.hat.addChild(this.fluff1);
        this.hat.addChild(this.fluff3);
        this.hat.addChild(this.fluff2);
        this.hat.addChild(this.fluff5);
        this.hat.addChild(this.fluff6);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
