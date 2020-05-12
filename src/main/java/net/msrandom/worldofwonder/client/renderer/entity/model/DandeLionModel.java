package net.msrandom.worldofwonder.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.msrandom.worldofwonder.entity.DandeLionEntity;

public class DandeLionModel extends SegmentedModel<DandeLionEntity> {
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
        this.tail.func_228301_a_(-1.0F, 0.0F, -2.0F, 2, 10, 2, 0.0F);
        this.tail.rotateAngleX = 0.17453292519943295F;
        this.head = new ModelRenderer(this, 49, 12);
        this.head.setRotationPoint(0.0F, -3.0F, -6.0F);
        this.head.func_228301_a_(-3.0F, -2.5F, -6.0F, 6, 6, 5, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 0);
        this.armLeft.mirror = true;
        this.armLeft.setRotationPoint(1.0F, 4.0F, -5.0F);
        this.armLeft.func_228301_a_(0.0F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.body.func_228301_a_(-4.0F, -4.0F, -7.0F, 8, 8, 14, 0.0F);
        this.legRight = new ModelRenderer(this, 0, 0);
        this.legRight.setRotationPoint(-1.0F, 4.0F, 5.0F);
        this.legRight.func_228301_a_(-3.0F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.mane = new ModelRenderer(this, 0, 24);
        this.mane.setRotationPoint(0.0F, 1.0F, -0.5F);
        this.mane.func_228301_a_(-5.5F, -5.5F, -3.5F, 11, 10, 7, 0.0F);
        this.snout = new ModelRenderer(this, 0, 42);
        this.snout.setRotationPoint(0.0F, 1.5F, -7.0F);
        this.snout.func_228301_a_(-2.0F, -1.0F, -1.0F, 4, 3, 3, 0.0F);
        this.armRight = new ModelRenderer(this, 0, 0);
        this.armRight.setRotationPoint(-1.0F, 4.0F, -5.0F);
        this.armRight.func_228301_a_(-3.0F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.legLeft = new ModelRenderer(this, 0, 0);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(1.0F, 4.0F, 5.0F);
        this.legLeft.func_228301_a_(0.0F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
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
    public Iterable<ModelRenderer> func_225601_a_() {
        return ImmutableList.of(body);
    }

    @Override
    public void func_225597_a_(DandeLionEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        this.legRight.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
        this.legLeft.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_;
        this.armRight.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_;
        this.armLeft.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
        this.tail.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 0.2F * p_225597_3_ + 0.17453292519943295F;
    }
}
