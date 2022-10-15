package earth.terrarium.worldofwonder.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.worldofwonder.WorldOfWonder;
import earth.terrarium.worldofwonder.client.renderer.entity.model.StemBoatModel;
import earth.terrarium.worldofwonder.entity.StemBoatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

public class StemBoatRenderer extends EntityRenderer<StemBoatEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/stem/boat.png");
    protected final StemBoatModel model = new StemBoatModel();

    public StemBoatRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn);
        this.shadowRadius = 0.8f;
    }

    @Override
    public void render(StemBoatEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight) {
        matrixStack.pushPose();
        matrixStack.translate(0.0D, 0.375D, 0.0D);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
        float f = (float)entity.getHurtTime() - partialTicks;
        float f1 = entity.getDamage() - partialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)entity.getHurtDir()));
        }

        float f2 = entity.getBubbleAngle(partialTicks);
        if (!Mth.equal(f2, 0.0F)) {
            matrixStack.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entity.getBubbleAngle(partialTicks), true));
        }

        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        this.model.setupAnim(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
        this.model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        VertexConsumer ivertexbuilder1 = bufferIn.getBuffer(RenderType.waterMask());
        this.model.getNoWater().render(matrixStack, ivertexbuilder1, packedLight, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, bufferIn, packedLight);
    }

    public ResourceLocation getTextureLocation(StemBoatEntity entity) {
        return TEXTURE;
    }
}

