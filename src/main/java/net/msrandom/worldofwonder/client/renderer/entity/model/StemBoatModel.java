package net.msrandom.worldofwonder.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.msrandom.worldofwonder.entity.StemBoatEntity;

import java.util.Arrays;

public class StemBoatModel extends SegmentedModel<StemBoatEntity> {
   private final ModelRenderer[] paddles = new ModelRenderer[2];
   private final ModelRenderer noWater;
   private final ImmutableList<ModelRenderer> parts;

   public StemBoatModel() {
      ModelRenderer[] amodelrenderer = new ModelRenderer[]{(new ModelRenderer(this, 0, 0)).setTexSize(128, 64), (new ModelRenderer(this, 0, 19)).setTexSize(128, 64), (new ModelRenderer(this, 0, 27)).setTexSize(128, 64), (new ModelRenderer(this, 0, 35)).setTexSize(128, 64), (new ModelRenderer(this, 0, 43)).setTexSize(128, 64)};
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
      this.noWater = (new ModelRenderer(this, 0, 0)).setTexSize(128, 64);
      this.noWater.addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F);
      this.noWater.setPos(0.0F, -3.0F, 1.0F);
      this.noWater.xRot = ((float)Math.PI / 2F);
      ImmutableList.Builder<ModelRenderer> builder = ImmutableList.builder();
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
   public ImmutableList<ModelRenderer> parts() {
      return parts;
   }

   public ModelRenderer getNoWater() {
      return this.noWater;
   }

   protected ModelRenderer makePaddle(boolean left) {
      ModelRenderer modelrenderer = (new ModelRenderer(this, 62, left ? 0 : 20)).setTexSize(128, 64);
      modelrenderer.addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F);
      modelrenderer.addBox(left ? -1.001F : 0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F);
      return modelrenderer;
   }

   protected void setRotations(StemBoatEntity entity, int side, float limbSwing) {
      float f = entity.getRowingTime(side, limbSwing);
      ModelRenderer modelrenderer = this.paddles[side];
      modelrenderer.xRot = (float) MathHelper.clampedLerp((-(float)Math.PI / 3F), -0.2617994F, ((MathHelper.sin(-f) + 1.0F) / 2.0F));
      modelrenderer.yRot = (float) MathHelper.clampedLerp((-(float)Math.PI / 4F), ((float)Math.PI / 4F), ((MathHelper.sin(-f + 1.0F) + 1.0F) / 2.0F));
      if (side == 1) {
         modelrenderer.yRot = (float)Math.PI - modelrenderer.yRot;
      }
   }
}
