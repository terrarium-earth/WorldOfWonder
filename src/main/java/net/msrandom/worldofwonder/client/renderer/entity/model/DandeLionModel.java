package net.msrandom.worldofwonder.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.msrandom.worldofwonder.entity.DandeLionEntity;

import java.util.Collections;

public class DandeLionModel extends AgeableModel<DandeLionEntity> {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer tail;
    public ModelRenderer mane;
    public ModelRenderer snout;

    public DandeLionModel() {
        this.textureWidth = 80;
        this.textureHeight = 80;
        this.tail = new ModelRenderer(this, 30, 0);
        this.tail.setRotationPoint(0.0F, -4.0F, 7.0F);
        this.tail.addBox(-1.0F, 0.0F, -2.0F, 2, 10, 2, 0.0F);
        this.tail.rotateAngleX = 0.17453292519943295F;
        this.head = new ModelRenderer(this, 49, 12);
        this.head.setRotationPoint(0.0F, -3.0F, -6.0F);
        this.head.addBox(-3.0F, -2.5F, -6.0F, 6, 6, 5, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 0);
        this.armLeft.mirror = true;
        this.armLeft.setRotationPoint(1.0F, 4.0F, -5.0F);
        this.armLeft.addBox(0.0F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.body.addBox(-4.0F, -4.0F, -7.0F, 8, 8, 14, 0.0F);
        this.legRight = new ModelRenderer(this, 0, 0);
        this.legRight.setRotationPoint(-1.0F, 4.0F, 5.0F);
        this.legRight.addBox(-3.0F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.mane = new ModelRenderer(this, 0, 24);
        this.mane.setRotationPoint(0.0F, 1.0F, -0.5F);
        this.mane.addBox(-5.5F, -5.5F, -3.5F, 11, 10, 7, 0.0F);
        this.snout = new ModelRenderer(this, 0, 42);
        this.snout.setRotationPoint(0.0F, 1.5F, -7.0F);
        this.snout.addBox(-2.0F, -1.0F, -1.0F, 4, 3, 3, 0.0F);
        this.armRight = new ModelRenderer(this, 0, 0);
        this.armRight.setRotationPoint(-1.0F, 4.0F, -5.0F);
        this.armRight.addBox(-3.0F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.legLeft = new ModelRenderer(this, 0, 0);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(1.0F, 4.0F, 5.0F);
        this.legLeft.addBox(0.0F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.body.addChild(this.tail);
        this.body.addChild(this.head);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.legRight);
        this.head.addChild(this.mane);
        this.head.addChild(this.snout);
        this.body.addChild(this.armRight);
        this.body.addChild(this.legLeft);
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
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.armRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.armLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.tail.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.2F * limbSwingAmount + 0.17453292519943295F;
    }
}
