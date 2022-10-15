package earth.terrarium.worldofwonder.client.renderer.tileentity;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class WonderItemRenderer extends BlockEntityWithoutLevelRenderer {
    private final Supplier<? extends BlockEntityType<?>> entitySupplier;
    private final Supplier<BlockEntity> entity;

    public WonderItemRenderer(Supplier<? extends BlockEntityType<?>> type) {
        entitySupplier = type;
        entity = Suppliers.memoize(() -> entitySupplier.get().create());
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemTransforms.TransformType transformType, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockEntityRenderDispatcher.instance.renderItem(entity.get(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
