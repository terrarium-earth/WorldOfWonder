package earth.terrarium.worldofwonder.client.renderer.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.worldofwonder.WorldOfWonder;
import earth.terrarium.worldofwonder.block.StemStandingSignBlock;
import earth.terrarium.worldofwonder.tileentity.StemSignTileEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

import java.util.List;

public class StemSignTileEntityRenderer extends BlockEntityRenderer<StemSignTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/stem/sign.png");
    private final SignRenderer.SignModel model = new SignRenderer.SignModel();

    public StemSignTileEntityRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    public void render(StemSignTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
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
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.model.renderType(TEXTURE));
        this.model.sign.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.model.stick.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        matrixStackIn.popPose();
        Font fontrenderer = this.renderer.getFont();
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
            FormattedCharSequence ireorderingprocessor = tileEntityIn.getRenderText(k1, (p_243502_1_) -> {
                List<FormattedCharSequence> list = fontrenderer.split(p_243502_1_, 90);
                return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
            });
            if (ireorderingprocessor != null) {
                float f3 = (float)(-fontrenderer.width(ireorderingprocessor) / 2);
                fontrenderer.drawInBatch(ireorderingprocessor, f3, (float)(k1 * 10 - 20), i1, false, matrixStackIn.last().pose(), bufferIn, false, 0, combinedLightIn);
            }
        }

        matrixStackIn.popPose();
    }
}
