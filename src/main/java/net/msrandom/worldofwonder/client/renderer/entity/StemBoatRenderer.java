/*
package net.msrandom.worldofwonder.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.client.renderer.entity.model.StemBoatModel;
import net.msrandom.worldofwonder.entity.StemBoatEntity;

public class StemBoatRenderer extends EntityRenderer<StemBoatEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/stem_boat.png");
    protected final StemBoatModel model = new StemBoatModel();

    public StemBoatRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.8f;
    }

    @Override
    public void render(StemBoatEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLight) {
        matrixStack.push();
        matrixStack.translate(0.0D, 0.375D, 0.0D);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F - partialTicks));
        float f = (float)entity.getTimeSinceHit() - partialTicks;
        float f1 = entity.getDamageTaken() - partialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            matrixStack.rotate(Vector3f.XP.rotationDegrees(MathHelper.sin(f) * f * f1 / 10.0F * (float)entity.getForwardDirection()));
        }

        float f2 = entity.getRockingAngle(partialTicks);
        if (!MathHelper.epsilonEquals(f2, 0.0F)) {
            matrixStack.rotate(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entity.getRockingAngle(partialTicks), true));
        }

        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0F));
        this.model.setRotationAngles(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(this.getEntityTexture(entity)));
        this.model.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        IVertexBuilder ivertexbuilder1 = bufferIn.getBuffer(RenderType.getWaterMask());
        this.model.getNoWater().render(matrixStack, ivertexbuilder1, packedLight, OverlayTexture.NO_OVERLAY);
        matrixStack.pop();
        super.render(entity, entityYaw, partialTicks, matrixStack, bufferIn, packedLight);
    }

    public ResourceLocation getEntityTexture(StemBoatEntity entity) {
        return TEXTURE;
    }
}
*/
