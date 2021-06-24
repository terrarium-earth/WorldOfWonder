package net.msrandom.worldofwonder.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.entity.DandeLionSeedEntity;

public class DandeLionSeedRenderer extends EntityRenderer<DandeLionSeedEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/dande_lion/seed.png");

    public DandeLionSeedRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(DandeLionSeedEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(matrixStackIn.last().pose());
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.enableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        IParticleRenderType renderType = IParticleRenderType.PARTICLE_SHEET_OPAQUE;
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        renderType.begin(buffer, textureManager);
        textureManager.bind(getTextureLocation(entityIn));
        RenderSystem.scalef(0.16f, 0.16f, 0.16f);
        buffer.vertex(-1, -1, 0).uv(1, 1).color(255, 255, 255, 255).uv2(packedLightIn).endVertex();
        buffer.vertex(-1, 1, 0).uv(1, 0).color(255, 255, 255, 255).uv2(packedLightIn).endVertex();
        buffer.vertex(1, 1, 0).uv(0, 0).color(255, 255, 255, 255).uv2(packedLightIn).endVertex();
        buffer.vertex(1, -1, 0).uv(0, 1).color(255, 255, 255, 255).uv2(packedLightIn).endVertex();
        renderType.end(tessellator);
        RenderSystem.popMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.disableFog();
    }

    @Override
    public ResourceLocation getTextureLocation(DandeLionSeedEntity entity) {
        return TEXTURE;
    }
}
