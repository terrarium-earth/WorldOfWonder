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
        this.texWidth = 64;
        this.texHeight = 64;
        this.armRight = new ModelRenderer(this, 0, 0);
        this.armRight.mirror = true;
        this.armRight.setPos(-3.0F, 6.0F, -5.5F);
        this.armRight.addBox(-2.0F, 0.0F, -2.5F, 4, 10, 5, 0.0F);
        this.tail = new ModelRenderer(this, 38, 0);
        this.tail.setPos(0.0F, -4.0F, 9.5F);
        this.tail.addBox(-1.0F, 0.0F, -2.0F, 2, 12, 2, 0.0F);
        this.tail.xRot = 0.17453292519943295F;
        this.legLeft = new ModelRenderer(this, 0, 0);
        this.legLeft.setPos(3.0F, 6.0F, 6.5F);
        this.legLeft.addBox(-2.0F, 0.0F, -2.5F, 4, 10, 5, 0.0F);
        this.snout = new ModelRenderer(this, 23, 47);
        this.snout.setPos(0.0F, 2.0F, -7.0F);
        this.snout.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 3, 0.0F);
        this.legRight = new ModelRenderer(this, 0, 0);
        this.legRight.mirror = true;
        this.legRight.setPos(-3.0F, 6.0F, 6.5F);
        this.legRight.addBox(-2.0F, 0.0F, -2.5F, 4, 10, 5, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 0);
        this.armLeft.setPos(3.0F, 6.0F, -5.5F);
        this.armLeft.addBox(-2.0F, 0.0F, -2.5F, 4, 10, 5, 0.0F);
        this.head = new ModelRenderer(this, 0, 47);
        this.head.setPos(0.0F, -3.0F, -7.0F);
        this.head.addBox(-4.0F, -4.0F, -7.0F, 8, 8, 7, 0.0F);
        this.mane = new ModelRenderer(this, 0, 28);
        this.mane.setPos(0.0F, 0.0F, -0.5F);
        this.mane.addBox(-6.0F, -6.0F, -3.5F, 12, 12, 7, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 8.0F, 0.0F);
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
    protected Iterable<ModelRenderer> headParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setupAnim(DandeLionEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.isSleeping()) {
            this.head.y = -2;
            this.head.z = -7;
            this.head.xRot = 0.785f;
            this.body.xRot = -0.785f;
            this.tail.y = -3;
            this.tail.z = 6;
            this.tail.xRot = 1.92f;
            this.armRight.y = 4;
            this.armRight.z = -4.5f;
            this.armRight.xRot = 0.785f;
            this.armRight.yRot = -0.087f;
            this.armRight.zRot = -0.087f;
            this.armLeft.y = 4;
            this.armLeft.z = -4.5f;
            this.armLeft.xRot = 0.785f;
            this.armLeft.yRot = 0.087f;
            this.armLeft.zRot = 0.087f;
            this.legRight.y = 5;
            this.legRight.xRot = -0.785f;
            this.legRight.yRot = 0.349f;
            this.legRight.zRot = 0.262f;
            this.legLeft.y = 5;
            this.legLeft.xRot = -0.785f;
            this.legLeft.yRot = -0.349f;
            this.legLeft.zRot = -0.262f;
        } else {
            this.head.y = -3;
            this.head.z = -6;
            this.head.xRot = 0;
            this.body.xRot = 0;
            this.tail.y = -4;
            this.tail.z = 9.5f;
            this.armRight.y = 6;
            this.armRight.z = -5.5f;
            this.armRight.yRot = 0;
            this.armRight.zRot = 0;
            this.armLeft.y = 6;
            this.armLeft.z = -5.5f;
            this.armLeft.yRot = 0;
            this.armLeft.zRot = 0;
            this.legRight.y = 6;
            this.legRight.yRot = 0;
            this.legRight.zRot = 0;
            this.legLeft.y = 6;
            this.legLeft.yRot = 0;
            this.legLeft.zRot = 0;

            this.legRight.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.legLeft.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.armRight.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.armLeft.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.tail.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.2F * limbSwingAmount + 0.17453292519943295F;
        }
    }
}
