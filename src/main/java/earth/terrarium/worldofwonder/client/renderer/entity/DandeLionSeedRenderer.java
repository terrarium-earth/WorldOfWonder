package earth.terrarium.worldofwonder.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.worldofwonder.WorldOfWonder;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import earth.terrarium.worldofwonder.entity.DandeLionSeedEntity;

public class DandeLionSeedRenderer extends EntityRenderer<DandeLionSeedEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/dande_lion/seed.png");
    private final ModelPart part = new ModelPart(6, 6, 0, 0);

    public DandeLionSeedRenderer(EntityRendererProvider.Context context) {
        super(context);
        part.addBox(0, 0, 0, 6, 6, 0f);
    }

    @Override
    public void render(DandeLionSeedEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        float scale = 0.4267f;
        matrixStackIn.scale(-scale, -scale, scale);
        part.render(matrixStackIn, bufferIn.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entityIn))), packedLightIn, OverlayTexture.NO_OVERLAY);
        RenderSystem.depthMask(true);
        matrixStackIn.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(DandeLionSeedEntity entity) {
        return TEXTURE;
    }
}
