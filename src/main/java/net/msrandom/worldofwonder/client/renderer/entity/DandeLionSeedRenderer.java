package net.msrandom.worldofwonder.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.entity.DandeLionSeedEntity;
import org.lwjgl.opengl.GL11;

public class DandeLionSeedRenderer extends EntityRenderer<DandeLionSeedEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/dande_lion/seed.png");

    public DandeLionSeedRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(DandeLionSeedEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        Minecraft.getInstance().getTextureManager().bindTexture(getEntityTexture(entityIn));
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        Matrix4f matrix = matrixStackIn.getLast().getMatrix();
        matrixStackIn.scale(0.16f, 0.16f, 0.16f);
        buffer.pos(matrix, -1, -1, 0).tex(1, 1).endVertex();
        buffer.pos(matrix, -1, 1, 0).tex(1, 0).endVertex();
        buffer.pos(matrix, 1, 1, 0).tex(0, 0).endVertex();
        buffer.pos(matrix, 1, -1, 0).tex(0, 1).endVertex();
        tessellator.draw();
        matrixStackIn.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(DandeLionSeedEntity entity) {
        return TEXTURE;
    }
}
