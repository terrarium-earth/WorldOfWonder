package net.msrandom.worldofwonder.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.entity.DandeLionSeedEntity;
import org.lwjgl.opengl.GL11;

public class DandeLionSeedRenderer extends EntityRenderer<DandeLionSeedEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/dande_lion/seed.png");
    private final ModelRenderer part = new ModelRenderer(6, 6, 0, 0);

    public DandeLionSeedRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        part.addBox(0, 0, 0, 6, 6, 0f);
    }

    @Override
    public void render(DandeLionSeedEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        float scale = 0.4267f;
        matrixStackIn.scale(scale, -scale, -scale);
        part.render(matrixStackIn, bufferIn.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entityIn))), packedLightIn, OverlayTexture.NO_OVERLAY);
        RenderSystem.depthMask(true);
        matrixStackIn.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(DandeLionSeedEntity entity) {
        return TEXTURE;
    }
}
