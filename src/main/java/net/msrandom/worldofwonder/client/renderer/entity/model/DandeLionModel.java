package net.msrandom.worldofwonder.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.msrandom.worldofwonder.entity.DandeLionEntity;

import java.util.Collections;

public class DandeLionModel extends AgeableModel<DandeLionEntity> {
    public ModelRenderer body;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer tail;
    public ModelRenderer head;
    public ModelRenderer mane;
    public ModelRenderer snout;

    public DandeLionModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.armRight = new ModelRenderer(this, 0, 0);
        this.armRight.mirror = true;
        this.armRight.setRotationPoint(-3.0F, 6.0F, -5.5F);
        this.armRight.addBox(-2.0F, 0.0F, -2.5F, 4, 10, 5, 0.0F);
        this.tail = new ModelRenderer(this, 38, 0);
        this.tail.setRotationPoint(0.0F, -4.0F, 9.5F);
        this.tail.addBox(-1.0F, 0.0F, -2.0F, 2, 12, 2, 0.0F);
        this.tail.rotateAngleX = 0.17453292519943295F;
        this.legLeft = new ModelRenderer(this, 0, 0);
        this.legLeft.setRotationPoint(3.0F, 6.0F, 6.5F);
        this.legLeft.addBox(-2.0F, 0.0F, -2.5F, 4, 10, 5, 0.0F);
        this.snout = new ModelRenderer(this, 23, 47);
        this.snout.setRotationPoint(0.0F, 2.0F, -7.0F);
        this.snout.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 3, 0.0F);
        this.legRight = new ModelRenderer(this, 0, 0);
        this.legRight.mirror = true;
        this.legRight.setRotationPoint(-3.0F, 6.0F, 6.5F);
        this.legRight.addBox(-2.0F, 0.0F, -2.5F, 4, 10, 5, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 0);
        this.armLeft.setRotationPoint(3.0F, 6.0F, -5.5F);
        this.armLeft.addBox(-2.0F, 0.0F, -2.5F, 4, 10, 5, 0.0F);
        this.head = new ModelRenderer(this, 0, 47);
        this.head.setRotationPoint(0.0F, -3.0F, -7.0F);
        this.head.addBox(-4.0F, -4.0F, -7.0F, 8, 8, 7, 0.0F);
        this.mane = new ModelRenderer(this, 0, 28);
        this.mane.setRotationPoint(0.0F, 0.0F, -0.5F);
        this.mane.addBox(-6.0F, -6.0F, -3.5F, 12, 12, 7, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.body.addBox(-5.0F, -4.0F, -9.0F, 10, 10, 18, 0.0F);
        this.body.addChild(this.armRight);
        this.body.addChild(this.tail);
        this.body.addChild(this.legLeft);
        this.head.addChild(this.snout);
        this.body.addChild(this.legRight);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.head);
        this.head.addChild(this.mane);
    }
    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setRotationAngles(DandeLionEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.isSitting()) {
            this.head.rotationPointY = -2;
            this.head.rotationPointZ = -7;
            this.head.rotateAngleX = 0.785f;
            this.body.rotateAngleX = -0.785f;
            this.tail.rotationPointY = -3;
            this.tail.rotationPointZ = 6;
            this.tail.rotateAngleX = 1.92f;
            this.armRight.rotationPointY = 4;
            this.armRight.rotationPointZ = -4.5f;
            this.armRight.rotateAngleX = 0.785f;
            this.armRight.rotateAngleY = -0.087f;
            this.armRight.rotateAngleZ = -0.087f;
            this.armLeft.rotationPointY = 4;
            this.armLeft.rotationPointZ = -4.5f;
            this.armLeft.rotateAngleX = 0.785f;
            this.armLeft.rotateAngleY = 0.087f;
            this.armLeft.rotateAngleZ = 0.087f;
            this.legRight.rotationPointY = 5;
            this.legRight.rotateAngleX = -0.785f;
            this.legRight.rotateAngleY = 0.349f;
            this.legRight.rotateAngleZ = 0.262f;
            this.legLeft.rotationPointY = 5;
            this.legLeft.rotateAngleX = -0.785f;
            this.legLeft.rotateAngleY = -0.349f;
            this.legLeft.rotateAngleZ = -0.262f;
        } else {
            this.head.rotationPointY = -3;
            this.head.rotationPointZ = -6;
            this.head.rotateAngleX = 0;
            this.body.rotateAngleX = 0;
            this.tail.rotationPointY = -4;
            this.tail.rotationPointZ = 9.5f;
            this.armRight.rotationPointY = 6;
            this.armRight.rotationPointZ = -5.5f;
            this.armRight.rotateAngleY = 0;
            this.armRight.rotateAngleZ = 0;
            this.armLeft.rotationPointY = 6;
            this.armLeft.rotationPointZ = -5.5f;
            this.armLeft.rotateAngleY = 0;
            this.armLeft.rotateAngleZ = 0;
            this.legRight.rotationPointY = 6;
            this.legRight.rotateAngleY = 0;
            this.legRight.rotateAngleZ = 0;
            this.legLeft.rotationPointY = 6;
            this.legLeft.rotateAngleY = 0;
            this.legLeft.rotateAngleZ = 0;

            this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.armRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.armLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.tail.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.2F * limbSwingAmount + 0.17453292519943295F;
        }
    }
}
