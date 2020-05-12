package net.msrandom.worldofwonder.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.client.renderer.entity.model.DandeLionModel;
import net.msrandom.worldofwonder.entity.DandeLionEntity;

public class DandeLionRenderer extends MobRenderer<DandeLionEntity, DandeLionModel> {
    private static final ResourceLocation FLUFF = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/dande_lion/fluff.png");
    private static final ResourceLocation NORMAL = new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/dande_lion/normal.png");

    public DandeLionRenderer(EntityRendererManager manager) {
        super(manager, new DandeLionModel(), 0.4f);
    }

    @Override
    public ResourceLocation getEntityTexture(DandeLionEntity entity) {
        return entity.isSheared() ? NORMAL : FLUFF;
    }
}
