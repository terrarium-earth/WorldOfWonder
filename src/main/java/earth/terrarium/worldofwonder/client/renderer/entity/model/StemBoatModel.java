package earth.terrarium.worldofwonder.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import earth.terrarium.worldofwonder.entity.StemBoatEntity;

import java.util.Arrays;

public class StemBoatModel extends ListModel<StemBoatEntity> {
   private final ModelPart[] paddles = new ModelPart[2];
   private final ModelPart noWater;
   private final ImmutableList<ModelPart> parts;

   public StemBoatModel() {
      ModelPart[] amodelrenderer = new ModelPart[]{(new ModelPart(this, 0, 0)).setTexSize(128, 64), (new ModelPart(this, 0, 19)).setTexSize(128, 64), (new ModelPart(this, 0, 27)).setTexSize(128, 64), (new ModelPart(this, 0, 35)).setTexSize(128, 64), (new ModelPart(this, 0, 43)).setTexSize(128, 64)};
      amodelrenderer[0].addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F);
      amodelrenderer[0].setPos(0.0F, 3.0F, 1.0F);
      amodelrenderer[1].addBox(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F, 0.0F);
      amodelrenderer[1].setPos(-15.0F, 4.0F, 4.0F);
      amodelrenderer[2].addBox(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F, 0.0F);
      amodelrenderer[2].setPos(15.0F, 4.0F, 0.0F);
      amodelrenderer[3].addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F);
      amodelrenderer[3].setPos(0.0F, 4.0F, -9.0F);
      amodelrenderer[4].addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F);
      amodelrenderer[4].setPos(0.0F, 4.0F, 9.0F);
      amodelrenderer[0].xRot = ((float)Math.PI / 2F);
      amodelrenderer[1].yRot = ((float)Math.PI * 1.5F);
      amodelrenderer[2].yRot = ((float)Math.PI / 2F);
      amodelrenderer[3].yRot = (float)Math.PI;
      this.paddles[0] = this.makePaddle(true);
      this.paddles[0].setPos(3.0F, -5.0F, 9.0F);
      this.paddles[1] = this.makePaddle(false);
      this.paddles[1].setPos(3.0F, -5.0F, -9.0F);
      this.paddles[1].yRot = (float)Math.PI;
      this.paddles[0].zRot = 0.19634955F;
      this.paddles[1].zRot = 0.19634955F;
      this.noWater = (new ModelPart(this, 0, 0)).setTexSize(128, 64);
      this.noWater.addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F);
      this.noWater.setPos(0.0F, -3.0F, 1.0F);
      this.noWater.xRot = ((float)Math.PI / 2F);
      ImmutableList.Builder<ModelPart> builder = ImmutableList.builder();
      builder.addAll(Arrays.asList(amodelrenderer));
      builder.addAll(Arrays.asList(this.paddles));
      this.parts = builder.build();
   }

   @Override
   public void setupAnim(StemBoatEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.setRotations(entity, 0, limbSwing);
      this.setRotations(entity, 1, limbSwing);
   }

   @Override
   public ImmutableList<ModelPart> parts() {
      return parts;
   }

   public ModelPart getNoWater() {
      return this.noWater;
   }

   protected ModelPart makePaddle(boolean left) {
      ModelPart modelrenderer = (new ModelPart(this, 62, left ? 0 : 20)).setTexSize(128, 64);
      modelrenderer.addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F);
      modelrenderer.addBox(left ? -1.001F : 0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F);
      return modelrenderer;
   }

   protected void setRotations(StemBoatEntity entity, int side, float limbSwing) {
      float f = entity.getRowingTime(side, limbSwing);
      ModelPart modelrenderer = this.paddles[side];
      modelrenderer.xRot = (float) Mth.clampedLerp((-(float)Math.PI / 3F), -0.2617994F, ((Mth.sin(-f) + 1.0F) / 2.0F));
      modelrenderer.yRot = (float) Mth.clampedLerp((-(float)Math.PI / 4F), ((float)Math.PI / 4F), ((Mth.sin(-f + 1.0F) + 1.0F) / 2.0F));
      if (side == 1) {
         modelrenderer.yRot = (float)Math.PI - modelrenderer.yRot;
      }
   }
}
