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
      ModelRenderer[] amodelrenderer = new ModelRenderer[]{(new ModelRenderer(this, 0, 0)).setTextureSize(128, 64), (new ModelRenderer(this, 0, 19)).setTextureSize(128, 64), (new ModelRenderer(this, 0, 27)).setTextureSize(128, 64), (new ModelRenderer(this, 0, 35)).setTextureSize(128, 64), (new ModelRenderer(this, 0, 43)).setTextureSize(128, 64)};
      amodelrenderer[0].func_228301_a_(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F);
      amodelrenderer[0].setRotationPoint(0.0F, 3.0F, 1.0F);
      amodelrenderer[1].func_228301_a_(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F, 0.0F);
      amodelrenderer[1].setRotationPoint(-15.0F, 4.0F, 4.0F);
      amodelrenderer[2].func_228301_a_(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F, 0.0F);
      amodelrenderer[2].setRotationPoint(15.0F, 4.0F, 0.0F);
      amodelrenderer[3].func_228301_a_(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F);
      amodelrenderer[3].setRotationPoint(0.0F, 4.0F, -9.0F);
      amodelrenderer[4].func_228301_a_(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F);
      amodelrenderer[4].setRotationPoint(0.0F, 4.0F, 9.0F);
      amodelrenderer[0].rotateAngleX = ((float)Math.PI / 2F);
      amodelrenderer[1].rotateAngleY = ((float)Math.PI * 1.5F);
      amodelrenderer[2].rotateAngleY = ((float)Math.PI / 2F);
      amodelrenderer[3].rotateAngleY = (float)Math.PI;
      this.paddles[0] = this.makePaddle(true);
      this.paddles[0].setRotationPoint(3.0F, -5.0F, 9.0F);
      this.paddles[1] = this.makePaddle(false);
      this.paddles[1].setRotationPoint(3.0F, -5.0F, -9.0F);
      this.paddles[1].rotateAngleY = (float)Math.PI;
      this.paddles[0].rotateAngleZ = 0.19634955F;
      this.paddles[1].rotateAngleZ = 0.19634955F;
      this.noWater = (new ModelRenderer(this, 0, 0)).setTextureSize(128, 64);
      this.noWater.func_228301_a_(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F);
      this.noWater.setRotationPoint(0.0F, -3.0F, 1.0F);
      this.noWater.rotateAngleX = ((float)Math.PI / 2F);
      ImmutableList.Builder<ModelRenderer> builder = ImmutableList.builder();
      builder.addAll(Arrays.asList(amodelrenderer));
      builder.addAll(Arrays.asList(this.paddles));
      this.parts = builder.build();
   }

   public void func_225597_a_(StemBoatEntity entity, float limbSwing, float limbSwingAmount, float age, float p_225597_5_, float p_225597_6_) {
      this.setRotations(entity, 0, limbSwing);
      this.setRotations(entity, 1, limbSwing);
   }

   public ImmutableList<ModelRenderer> func_225601_a_() {
      return this.parts;
   }

   public ModelRenderer getNoWater() {
      return this.noWater;
   }

   protected ModelRenderer makePaddle(boolean left) {
      ModelRenderer modelrenderer = (new ModelRenderer(this, 62, left ? 0 : 20)).setTextureSize(128, 64);
      modelrenderer.func_228300_a_(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F);
      modelrenderer.func_228300_a_(left ? -1.001F : 0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F);
      return modelrenderer;
   }

   protected void setRotations(StemBoatEntity entity, int side, float limbSwing) {
      float f = entity.getRowingTime(side, limbSwing);
      ModelRenderer modelrenderer = this.paddles[side];
      modelrenderer.rotateAngleX = (float) MathHelper.clampedLerp((-(float)Math.PI / 3F), -0.2617994F, ((MathHelper.sin(-f) + 1.0F) / 2.0F));
      modelrenderer.rotateAngleY = (float) MathHelper.clampedLerp((-(float)Math.PI / 4F), ((float)Math.PI / 4F), ((MathHelper.sin(-f + 1.0F) + 1.0F) / 2.0F));
      if (side == 1) {
         modelrenderer.rotateAngleY = (float)Math.PI - modelrenderer.rotateAngleY;
      }
   }
}
