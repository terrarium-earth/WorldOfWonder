package earth.terrarium.worldofwonder.client.renderer.entity.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DandelionHatModel extends HumanoidModel<LivingEntity> {
    public static final DandelionHatModel INSTANCE = new DandelionHatModel();

    public ModelPart hat;
    public ModelPart fluff1;
    public ModelPart fluff2;
    public ModelPart fluff3;
    public ModelPart fluff4;
    public ModelPart fluff5;
    public ModelPart fluff6;
    public ModelPart fluff7;
    public ModelPart fluff8;

    public DandelionHatModel() {
        super(0);
        this.texWidth = 150;
        this.texHeight = 128;
        this.fluff7 = new ModelPart(this, 64, 99);
        this.fluff7.setPos(-3.0F, -8.5F, -3.0F);
        this.fluff7.addBox(-4.5F, 0.0F, -10.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff7, -0.4363323129985824F, 0.7853981633974483F, 0.0F);
        this.fluff8 = new ModelPart(this, 64, 111);
        this.fluff8.setPos(3.0F, -8.5F, -3.0F);
        this.fluff8.addBox(-4.5F, 0.0F, -10.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff8, -0.4363323129985824F, -0.7853981633974483F, 0.0F);
        this.rightLeg = new ModelPart(this, 0, 16);
        this.rightLeg.setPos(-1.899999976158142F, 12.0F, 0.0F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.leftArm = new ModelPart(this, 32, 48);
        this.leftArm.setPos(5.0F, 2.0F, 0.0F);
        this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.hat = new ModelPart(this, 94, 64);
        this.hat.setPos(0.0F, 0.0F, 0.0F);
        this.hat.addBox(-5.0F, -9.5F, -5.0F, 10, 4, 10, 0.0F);
        this.fluff4 = new ModelPart(this, 64, 88);
        this.fluff4.mirror = true;
        this.fluff4.setPos(5.0F, -8.5F, 0.0F);
        this.fluff4.addBox(0.0F, 0.0F, -4.5F, 10, 0, 9, 0.0F);
        this.setRotateAngle(fluff4, 0.0F, 0.0F, -0.4363323129985824F);
        this.rightArm = new ModelPart(this, 40, 16);
        this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
        this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.fluff1 = new ModelPart(this, 64, 64);
        this.fluff1.setPos(0.0F, -8.5F, 5.0F);
        this.fluff1.addBox(-4.5F, 0.0F, 0.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff1, 0.4363323129985824F, 0.0F, 0.0F);
        this.fluff3 = new ModelPart(this, 64, 76);
        this.fluff3.setPos(0.0F, -8.5F, -5.0F);
        this.fluff3.addBox(-4.5F, 0.0F, -10.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff3, -0.4363323129985824F, 0.0F, 0.0F);
        this.fluff2 = new ModelPart(this, 64, 88);
        this.fluff2.setPos(-5.0F, -8.5F, 0.0F);
        this.fluff2.addBox(-10.0F, 0.0F, -4.5F, 10, 0, 9, 0.0F);
        this.setRotateAngle(fluff2, 0.0F, 0.0F, 0.4363323129985824F);
        this.leftLeg = new ModelPart(this, 16, 48);
        this.leftLeg.setPos(1.899999976158142F, 12.0F, 0.0F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.fluff5 = new ModelPart(this, 84, 111);
        this.fluff5.setPos(3.0F, -8.5F, 3.0F);
        this.fluff5.addBox(-4.5F, 0.0F, 0.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff5, 0.4363323129985824F, 0.7853981633974483F, 0.0F);
        this.fluff6 = new ModelPart(this, 84, 99);
        this.fluff6.setPos(-3.0F, -8.5F, 3.0F);
        this.fluff6.addBox(-4.5F, 0.0F, 0.0F, 9, 0, 10, 0.0F);
        this.setRotateAngle(fluff6, 0.4363323129985824F, -0.7853981633974483F, 0.0F);
        this.body = new ModelPart(this, 16, 16);
        this.body.setPos(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.hat.addChild(this.fluff7);
        this.hat.addChild(this.fluff8);
        this.head.addChild(this.hat);
        this.hat.addChild(this.fluff4);
        this.hat.addChild(this.fluff1);
        this.hat.addChild(this.fluff3);
        this.hat.addChild(this.fluff2);
        this.hat.addChild(this.fluff5);
        this.hat.addChild(this.fluff6);
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
