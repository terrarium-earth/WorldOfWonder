package net.msrandom.worldofwonder.client.renderer.tileentity;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class WonderItemRenderer extends ItemStackTileEntityRenderer {
    private final Supplier<? extends TileEntityType<?>> entitySupplier;
    private final Supplier<TileEntity> entity;

    public WonderItemRenderer(Supplier<? extends TileEntityType<?>> type) {
        entitySupplier = type;
        entity = Suppliers.memoize(() -> entitySupplier.get().create());
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TileEntityRendererDispatcher.instance.renderItem(entity.get(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
