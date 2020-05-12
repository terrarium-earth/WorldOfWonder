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

    public void func_225623_a_(StemBoatEntity entity, float partialTicks, float limbSwing, MatrixStack matrixStack, IRenderTypeBuffer type, int light) {
        matrixStack.func_227860_a_();
        matrixStack.func_227861_a_(0.0D, 0.375D, 0.0D);
        matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F - partialTicks));
        float f = (float)entity.getTimeSinceHit() - limbSwing;
        float f1 = entity.getDamageTaken() - limbSwing;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(MathHelper.sin(f) * f * f1 / 10.0F * (float)entity.getForwardDirection()));
        }

        float f2 = entity.getRockingAngle(limbSwing);
        if (!MathHelper.epsilonEquals(f2, 0.0F)) {
            matrixStack.func_227863_a_(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entity.getRockingAngle(limbSwing), true));
        }

        matrixStack.func_227862_a_(-1.0F, -1.0F, 1.0F);
        matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(90.0F));
        this.model.func_225597_a_(entity, limbSwing, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = type.getBuffer(this.model.func_228282_a_(this.getEntityTexture(entity)));
        this.model.func_225598_a_(matrixStack, ivertexbuilder, light, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, 1.0F);
        IVertexBuilder ivertexbuilder1 = type.getBuffer(RenderType.func_228651_i_());
        this.model.getNoWater().func_228308_a_(matrixStack, ivertexbuilder1, light, OverlayTexture.field_229196_a_);
        matrixStack.func_227865_b_();
        super.func_225623_a_(entity, partialTicks, limbSwing, matrixStack, type, light);
    }

    public ResourceLocation getEntityTexture(StemBoatEntity entity) {
        return TEXTURE;
    }
}
