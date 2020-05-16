package net.msrandom.worldofwonder.client.renderer.tileentity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.tileentity.StemSignTileEntity;
import net.msrandom.worldofwonder.block.StemStandingSignBlock;

import java.util.List;

public class StemSignTileEntityRenderer extends TileEntityRenderer<StemSignTileEntity> {
   private final SignTileEntityRenderer.SignModel model = new SignTileEntityRenderer.SignModel();
   // Atlases.SIGN_ATLAS == Atlases.field_228746_e_ maybe
   private final Material material = new Material(Atlases.field_228746_e_, new ResourceLocation(WorldOfWonder.MOD_ID, "entity/stem_sign.png"));

   public StemSignTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
      super(rendererDispatcherIn);
   }

   @Override
   public void func_225616_a_(StemSignTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
      BlockState blockstate = tileEntityIn.getBlockState();
      //MatrixStack.push() == MatrixStack.func_227860_a_() 
      matrixStackIn.func_227860_a_();
      float f = 0.6666667F;
      if (blockstate.getBlock() instanceof StemStandingSignBlock) {
    	  //MatrixStack.translate == MatrixStack.func_227861_a_
         matrixStackIn.func_227861_a_(0.5D, 0.5D, 0.5D);
         float f1 = -((float) (blockstate.get(StandingSignBlock.ROTATION) * 360) / 16.0F);
         //MatrixStack.rotate == MatrixStack.func_227863_a_ 
         //Vector3f.YP == Vector3f.field_229178_a_
         //Vector3f.field_229178_a_.rotationDegrees == Vector3f.field_229178_a_.func_229187_a_
         matrixStackIn.func_227863_a_(Vector3f.field_229178_a_.func_229187_a_(f1));
         //model.signStick == model.field_78165_b
         this.model.field_78165_b.showModel = true;
      } else {
         matrixStackIn.func_227861_a_(0.5D, 0.5D, 0.5D);
         float f4 = -blockstate.get(WallSignBlock.FACING).getHorizontalAngle();
         matrixStackIn.func_227863_a_(Vector3f.field_229178_a_.func_229187_a_(f4));
         matrixStackIn.func_227861_a_(0.0D, -0.3125D, -0.4375D);
         this.model.field_78165_b.showModel = false;
      }

      matrixStackIn.func_227860_a_();
      //MatrixStack.scale == MatrixStack.func_227862_a_  
      matrixStackIn.func_227862_a_(0.6666667F, -0.6666667F, -0.6666667F);
      // material.getBuffer == material.func_229311_a_
      IVertexBuilder ivertexbuilder = material.func_229311_a_(bufferIn, this.model::getRenderType);
      // model.signBoard == model.func_227862_a_
      // model.render == model.func_228308_a_
      this.model.field_78166_a.func_228308_a_(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
      this.model.field_78165_b.func_228308_a_(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
      //MatrixStack.pop == MatrixStack.func_227865_b_
      matrixStackIn.func_227865_b_();
      FontRenderer fontrenderer = this.renderDispatcher.getFontRenderer();
      float f2 = 0.010416667F;
      matrixStackIn.func_227861_a_(0.0D, (double) 0.33333334F, (double) 0.046666667F);
      matrixStackIn.func_227862_a_(0.010416667F, -0.010416667F, 0.010416667F);
      int i = tileEntityIn.getTextColor().getTextColor();
      double d0 = 0.4D;
      // NativeImage.getRed = NativeImage.func_227791_b_
      int j = (int) ((double) NativeImage.func_227791_b_(i) * 0.4D);
      // NativeImage.getGreen == NativeImage.func_227793_c_
      int k = (int) ((double) NativeImage.func_227793_c_(i) * 0.4D);
      // NativeImage.getBlue == NativeImage.func_227795_d_
      int l = (int) ((double) NativeImage.func_227795_d_(i) * 0.4D);
      // NativeImage.getCombined == NativeImage.func_227787_a_
      int i1 = NativeImage.func_227787_a_(0, l, k, j);

      for (int j1 = 0; j1 < 4; ++j1) {
         String s = tileEntityIn.getRenderText(j1, (p_212491_1_) -> {
            List<ITextComponent> list = RenderComponentsUtil.splitText(p_212491_1_, 90, fontrenderer, false, true);
            return list.isEmpty() ? "" : list.get(0).getFormattedText();
         });
         if (s != null) {
            float f3 = (float) (-fontrenderer.getStringWidth(s) / 2);
            fontrenderer.renderString(s, f3, (float) (j1 * 10 - tileEntityIn.signText.length * 5), i1, false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, combinedLightIn);
         }
      }

      matrixStackIn.pop();
   }


}