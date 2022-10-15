package earth.terrarium.worldofwonder.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import earth.terrarium.worldofwonder.entity.DandeLionEntity;

import java.util.Collections;

public class DandeLionModel extends AgeableListModel<DandeLionEntity> {
    public ModelPart body;
    public ModelPart armLeft;
    public ModelPart armRight;
    public ModelPart legLeft;
    public ModelPart legRight;
    public ModelPart head;
    public ModelPart tail;
    public ModelPart snout;
    public ModelPart earRight;
    public ModelPart earLeft;
    public ModelPart mane;
    public ModelPart maneExtra;

    public DandeLionModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.maneExtra = new ModelPart(this, 0, 40);
        this.maneExtra.setPos(0.0F, 9.0F, 3.5F);
        this.maneExtra.addBox(-4.5F, 0.0F, -3.5F, 9.0F, 2.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.earRight = new ModelPart(this, 25, 22);
        this.earRight.setPos(-2.5F, -2.0F, -2.5F);
        this.earRight.addBox(-2.0F, -2.0F, -0.5F, 3.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.legRight = new ModelPart(this, 0, 0);
        this.legRight.setPos(-2.5F, 4.0F, 5.0F);
        this.legRight.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 49);
        this.head.setPos(0.0F, -3.0F, -7.0F);
        this.head.addBox(-3.5F, -3.0F, -5.0F, 7.0F, 6.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.snout = new ModelPart(this, 24, 49);
        this.snout.setPos(0.0F, 2.0F, -5.0F);
        this.snout.addBox(-2.5F, -1.0F, -2.0F, 5.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.legLeft = new ModelPart(this, 0, 0);
        this.legLeft.setPos(2.5F, 4.0F, 5.0F);
        this.legLeft.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.mane = new ModelPart(this, 0, 22);
        this.mane.setPos(0.0F, -3.0F, -2.0F);
        this.mane.addBox(-4.5F, -2.0F, 0.0F, 9.0F, 11.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.armLeft = new ModelPart(this, 0, 0);
        this.armLeft.setPos(2.5F, 4.0F, -5.0F);
        this.armLeft.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelPart(this, 30, 0);
        this.tail.setPos(0.0F, -3.0F, 7.5F);
        this.tail.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 11.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 0.17453292519943295F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 12.0F, 0.0F);
        this.body.addBox(-4.0F, -4.0F, -7.0F, 8.0F, 8.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.earLeft = new ModelPart(this, 25, 22);
        this.earLeft.setPos(2.5F, -2.0F, -2.5F);
        this.earLeft.addBox(-1.0F, -2.0F, -0.5F, 3.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.armRight = new ModelPart(this, 0, 0);
        this.armRight.setPos(-2.5F, 4.0F, -5.0F);
        this.armRight.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.mane.addChild(this.maneExtra);
        this.head.addChild(this.earRight);
        this.body.addChild(this.legRight);
        this.body.addChild(this.head);
        this.head.addChild(this.snout);
        this.body.addChild(this.legLeft);
        this.head.addChild(this.mane);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.tail);
        this.head.addChild(this.earLeft);
        this.body.addChild(this.armRight);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setupAnim(DandeLionEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.0f;

        if (entityIn.isInSittingPose()) {
            this.legRight.y = 2.0F;
            this.legRight.xRot = 1.5708F;
            this.legRight.yRot = -0.4363F;

            this.legLeft.y = 2.0F;
            this.legLeft.xRot = 1.5708F;
            this.legLeft.yRot = 0.4363F;

            this.armRight.y = 2.0F;
            this.armRight.xRot = -1.5708F;
            this.armRight.yRot = 0.4363F;

            this.armLeft.y = 2.0F;
            this.armLeft.xRot = -1.5708F;
            this.armLeft.yRot = -0.4363F;

            this.body.y = 14.5F;
            this.body.xRot = 0.0F;

            this.head.y = -5.0F;

            this.tail.xRot = 1.0471975511965976F;

        } else {
            this.body.y = Mth.cos(limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount + 12.02F;
            this.body.xRot = Mth.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount;
            this.head.y = Mth.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.05F * limbSwingAmount - 3.05F;
            this.armLeft.xRot = Mth.cos(-2.0F + limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount;
            this.armRight.xRot = Mth.cos(3.0F + limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount;
            this.legLeft.xRot = Mth.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount;
            this.legRight.xRot = Mth.cos(2.0F + limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount;
            this.tail.xRot = Mth.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.4F;

            this.armLeft.yRot = 0.0F;
            this.armRight.yRot = 0.0F;
            this.legLeft.yRot = 0.0F;
            this.legRight.yRot = 0.0F;

            this.armLeft.y = 4.0F;
            this.armRight.y = 4.0F;
            this.legLeft.y = 4.0F;
            this.legRight.y = 4.0F;

        }

        this.earRight.zRot = Mth.cos(-1.0F + ageInTicks * speed * 0.15F) * degree * 0.2F - 0.1F;
        this.earLeft.zRot = Mth.cos(-1.0F + ageInTicks * speed * 0.15F) * degree * -0.2F + 0.1F;
        this.tail.zRot = Mth.cos(ageInTicks * 0.25F) * 0.25F;
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
