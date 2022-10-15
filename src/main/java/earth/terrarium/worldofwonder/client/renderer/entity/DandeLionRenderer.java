package earth.terrarium.worldofwonder.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.worldofwonder.WorldOfWonder;
import earth.terrarium.worldofwonder.client.renderer.entity.model.DandeLionModel;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import earth.terrarium.worldofwonder.entity.DandeLionEntity;

public class DandeLionRenderer extends MobRenderer<DandeLionEntity, DandeLionModel> {
    private static final ResourceLocation FLUFF = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/dande_lion/fluff.png");
    private static final ResourceLocation NORMAL = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/dande_lion/normal.png");

    public DandeLionRenderer(EntityRendererProvider.Context context) {
        super(context, new DandeLionModel(), 0.4f);
    }

    @Override
    protected void scale(DandeLionEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        super.scale(entitylivingbaseIn, matrixStackIn, partialTickTime);
        if (entitylivingbaseIn.isInSittingPose()) matrixStackIn.translate(0, entitylivingbaseIn.isBaby() ? 0.175 : 0.35, 0);
    }

    @Override
    public ResourceLocation getTextureLocation(DandeLionEntity entity) {
        return entity.isSheared() ? NORMAL : FLUFF;
    }
}
