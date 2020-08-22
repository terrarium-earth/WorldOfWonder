package net.msrandom.worldofwonder.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.*;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.tileentity.StemChestTileEntity;

import java.util.Calendar;

public class StemChestTileEntityRenderer extends TileEntityRenderer<StemChestTileEntity> {
    private static final ResourceLocation CHEST_XMAS = getChestTexture("christmas");
    private static final ResourceLocation CHEST_XMAS_LEFT = getChestTexture("christmas_left");
    private static final ResourceLocation CHEST_XMAS_RIGHT = getChestTexture("christmas_right");
    private static final ResourceLocation CHEST_STEM = getStemTexture("chest");
    private static final ResourceLocation CHEST_STEM_LEFT = getStemTexture("chest_left");
    private static final ResourceLocation CHEST_STEM_RIGHT = getStemTexture("chest_right");
    private static final ResourceLocation CHEST_TRAPPED_STEM = getStemTexture("chest_trapped");
    private static final ResourceLocation CHEST_TRAPPED_STEM_LEFT = getStemTexture("chest_trapped_left");
    private static final ResourceLocation CHEST_TRAPPED_STEM_RIGHT = getStemTexture("chest_trapped_right");
    
    private final ModelRenderer singleLid;
    private final ModelRenderer singleBottom;
    private final ModelRenderer singleLatch;
    private final ModelRenderer rightLid;
    private final ModelRenderer rightBottom;
    private final ModelRenderer rightLatch;
    private final ModelRenderer leftLid;
    private final ModelRenderer leftBottom;
    private final ModelRenderer leftLatch;
    private boolean isChristmas;

    public StemChestTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) {
            this.isChristmas = true;
        }

        this.singleBottom = new ModelRenderer(64, 64, 0, 19);
        this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
        this.singleLid = new ModelRenderer(64, 64, 0, 0);
        this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
        this.singleLid.rotationPointY = 9.0F;
        this.singleLid.rotationPointZ = 1.0F;
        this.singleLatch = new ModelRenderer(64, 64, 0, 0);
        this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
        this.singleLatch.rotationPointY = 8.0F;
        this.rightBottom = new ModelRenderer(64, 64, 0, 19);
        this.rightBottom.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.rightLid = new ModelRenderer(64, 64, 0, 0);
        this.rightLid.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.rightLid.rotationPointY = 9.0F;
        this.rightLid.rotationPointZ = 1.0F;
        this.rightLatch = new ModelRenderer(64, 64, 0, 0);
        this.rightLatch.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.rightLatch.rotationPointY = 8.0F;
        this.leftBottom = new ModelRenderer(64, 64, 0, 19);
        this.leftBottom.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.leftLid = new ModelRenderer(64, 64, 0, 0);
        this.leftLid.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.leftLid.rotationPointY = 9.0F;
        this.leftLid.rotationPointZ = 1.0F;
        this.leftLatch = new ModelRenderer(64, 64, 0, 0);
        this.leftLatch.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.leftLatch.rotationPointY = 8.0F;
    }

    public void render(StemChestTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        World world = tileEntityIn.getWorld();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.has(ChestBlock.TYPE) ? blockstate.get(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockstate.getBlock();
        if (block instanceof AbstractChestBlock) {
            AbstractChestBlock<?> abstractchestblock = (AbstractChestBlock<?>)block;
            boolean flag1 = chesttype != ChestType.SINGLE;
            matrixStackIn.push();
            float f = blockstate.get(ChestBlock.FACING).getHorizontalAngle();
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f));
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> icallbackwrapper;
            if (flag) {
                icallbackwrapper = abstractchestblock.func_225536_a_(blockstate, world, tileEntityIn.getPos(), true);
            } else {
                icallbackwrapper = TileEntityMerger.ICallback::func_225537_b_;
            }

            float f1 = icallbackwrapper.apply(ChestBlock.func_226917_a_((IChestLid)tileEntityIn)).get(partialTicks);
            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            int i = icallbackwrapper.apply(new DualBrightnessCallback<>()).applyAsInt(combinedLightIn);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutout(this.getTexture(tileEntityIn, chesttype)));
            if (flag1) {
                if (chesttype == ChestType.LEFT) {
                    this.renderModels(matrixStackIn, ivertexbuilder, this.leftLid, this.leftLatch, this.leftBottom, f1, i, combinedOverlayIn);
                } else {
                    this.renderModels(matrixStackIn, ivertexbuilder, this.rightLid, this.rightLatch, this.rightBottom, f1, i, combinedOverlayIn);
                }
            } else {
                this.renderModels(matrixStackIn, ivertexbuilder, this.singleLid, this.singleLatch, this.singleBottom, f1, i, combinedOverlayIn);
            }

            matrixStackIn.pop();
        }
    }

    private void renderModels(MatrixStack matrixStackIn, IVertexBuilder bufferIn, ModelRenderer chestLid, ModelRenderer chestLatch, ModelRenderer chestBottom, float lidAngle, int combinedLightIn, int combinedOverlayIn) {
        chestLid.rotateAngleX = -(lidAngle * ((float)Math.PI / 2F));
        chestLatch.rotateAngleX = chestLid.rotateAngleX;
        chestLid.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        chestLatch.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        chestBottom.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    protected ResourceLocation getTexture(StemChestTileEntity tileEntity, ChestType chestType) {
        if (isChristmas) {
            return getChestTexture(chestType, CHEST_XMAS, CHEST_XMAS_LEFT, CHEST_XMAS_RIGHT);
        } else {
            return tileEntity.isTrapped() ? getChestTexture(chestType, CHEST_TRAPPED_STEM, CHEST_TRAPPED_STEM_LEFT, CHEST_TRAPPED_STEM_RIGHT) : getChestTexture(chestType, CHEST_STEM, CHEST_STEM_LEFT, CHEST_STEM_RIGHT);
        }
    }
    
    private static ResourceLocation getChestTexture(ChestType type, ResourceLocation single, ResourceLocation left, ResourceLocation right) {
        switch(type) {
            case LEFT:
                return left;
            case RIGHT:
                return right;
            case SINGLE:
            default:
                return single;
        }
    }

    private static ResourceLocation getChestTexture(String name) {
        return new ResourceLocation("minecraft", "textures/entity/chest/" + name + ".png");
    }

    private static ResourceLocation getStemTexture(String name) {
        return new ResourceLocation(WorldOfWonder.MOD_ID, "textures/entity/stem/" + name + ".png");
    }
}
