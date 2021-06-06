package net.msrandom.worldofwonder.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.StemStandingSignBlock;
import net.msrandom.worldofwonder.tileentity.StemSignTileEntity;

import java.util.List;

public class StemSignTileEntityRenderer extends TileEntityRenderer<StemSignTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/stem/sign.png");
    private final SignTileEntityRenderer.SignModel model = new SignTileEntityRenderer.SignModel();

    public StemSignTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    public void render(StemSignTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = tileEntityIn.getBlockState();
        matrixStackIn.pushPose();
        float f = 2f / 3f;
        if (blockstate.getBlock() instanceof StemStandingSignBlock) {
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            float f1 = -((float) (blockstate.getValue(StandingSignBlock.ROTATION) * 360) / 16.0F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
            this.model.stick.visible = true;
        } else {
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            float f4 = -blockstate.getValue(WallSignBlock.FACING).toYRot();
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f4));
            matrixStackIn.translate(0.0D, -0.3125D, -0.4375D);
            this.model.stick.visible = false;
        }

        matrixStackIn.pushPose();
        matrixStackIn.scale(f, -f, -f);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.renderType(TEXTURE));
        this.model.sign.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.model.stick.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        matrixStackIn.popPose();
        FontRenderer fontrenderer = this.renderer.getFont();
        float f2 = 0.010416667F;
        matrixStackIn.translate(0.0D, 0.33333334F, 0.046666667F);
        matrixStackIn.scale(f2, -f2, f2);
        int i = tileEntityIn.getTextColor().getTextColor();
        double d0 = 0.4D;
        int j = (int) ((double) NativeImage.getR(i) * d0);
        int k = (int) ((double) NativeImage.getG(i) * d0);
        int l = (int) ((double) NativeImage.getB(i) * d0);
        int i1 = NativeImage.combine(0, l, k, j);

        for(int k1 = 0; k1 < 4; ++k1) {
            IReorderingProcessor ireorderingprocessor = tileEntityIn.getRenderText(k1, (p_243502_1_) -> {
                List<IReorderingProcessor> list = fontrenderer.split(p_243502_1_, 90);
                return list.isEmpty() ? IReorderingProcessor.EMPTY : list.get(0);
            });
            if (ireorderingprocessor != null) {
                float f3 = (float)(-fontrenderer.width(ireorderingprocessor) / 2);
                fontrenderer.drawInBatch(ireorderingprocessor, f3, (float)(k1 * 10 - 20), i1, false, matrixStackIn.last().pose(), bufferIn, false, 0, combinedLightIn);
            }
        }

        matrixStackIn.popPose();
    }
}
