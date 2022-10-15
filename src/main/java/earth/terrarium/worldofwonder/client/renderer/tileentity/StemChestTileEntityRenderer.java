package earth.terrarium.worldofwonder.client.renderer.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.worldofwonder.WorldOfWonder;
import earth.terrarium.worldofwonder.tileentity.StemChestTileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;

import java.util.Calendar;

import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;

public class StemChestTileEntityRenderer implements BlockEntityRenderer<StemChestTileEntity> {
    private static final ResourceLocation CHEST_XMAS = getChestTexture("christmas");
    private static final ResourceLocation CHEST_XMAS_LEFT = getChestTexture("christmas_left");
    private static final ResourceLocation CHEST_XMAS_RIGHT = getChestTexture("christmas_right");
    private static final ResourceLocation CHEST_STEM = getStemTexture("chest");
    private static final ResourceLocation CHEST_STEM_LEFT = getStemTexture("chest_left");
    private static final ResourceLocation CHEST_STEM_RIGHT = getStemTexture("chest_right");
    private static final ResourceLocation CHEST_TRAPPED_STEM = getStemTexture("chest_trapped");
    private static final ResourceLocation CHEST_TRAPPED_STEM_LEFT = getStemTexture("chest_trapped_left");
    private static final ResourceLocation CHEST_TRAPPED_STEM_RIGHT = getStemTexture("chest_trapped_right");
    
    private final ModelPart singleLid;
    private final ModelPart singleBottom;
    private final ModelPart singleLatch;
    private final ModelPart rightLid;
    private final ModelPart rightBottom;
    private final ModelPart rightLatch;
    private final ModelPart leftLid;
    private final ModelPart leftBottom;
    private final ModelPart leftLatch;
    private boolean isChristmas;

    public StemChestTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        super();
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) {
            this.isChristmas = true;
        }

        this.singleBottom = new ModelPart(64, 64, 0, 19);
        this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
        this.singleLid = new ModelPart(64, 64, 0, 0);
        this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
        this.singleLid.y = 9.0F;
        this.singleLid.z = 1.0F;
        this.singleLatch = new ModelPart(64, 64, 0, 0);
        this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
        this.singleLatch.y = 8.0F;
        this.rightBottom = new ModelPart(64, 64, 0, 19);
        this.rightBottom.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.rightLid = new ModelPart(64, 64, 0, 0);
        this.rightLid.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.rightLid.y = 9.0F;
        this.rightLid.z = 1.0F;
        this.rightLatch = new ModelPart(64, 64, 0, 0);
        this.rightLatch.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.rightLatch.y = 8.0F;
        this.leftBottom = new ModelPart(64, 64, 0, 19);
        this.leftBottom.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.leftLid = new ModelPart(64, 64, 0, 0);
        this.leftLid.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.leftLid.y = 9.0F;
        this.leftLid.z = 1.0F;
        this.leftLatch = new ModelPart(64, 64, 0, 0);
        this.leftLatch.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.leftLatch.y = 8.0F;
    }

    public void render(StemChestTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level world = tileEntityIn.getLevel();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.hasProperty(ChestBlock.TYPE) ? blockstate.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockstate.getBlock();
        if (block instanceof AbstractChestBlock) {
            AbstractChestBlock<?> abstractchestblock = (AbstractChestBlock<?>)block;
            boolean flag1 = chesttype != ChestType.SINGLE;
            matrixStackIn.pushPose();
            float f = blockstate.getValue(ChestBlock.FACING).toYRot();
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f));
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> icallbackwrapper;
            if (flag) {
                icallbackwrapper = abstractchestblock.combine(blockstate, world, tileEntityIn.getBlockPos(), true);
            } else {
                icallbackwrapper = DoubleBlockCombiner.Combiner::acceptNone;
            }

            float f1 = icallbackwrapper.apply(ChestBlock.opennessCombiner(tileEntityIn)).get(partialTicks);
            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            int i = icallbackwrapper.apply(new BrightnessCombiner<>()).applyAsInt(combinedLightIn);
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutout(this.getTexture(tileEntityIn, chesttype)));
            if (flag1) {
                if (chesttype == ChestType.LEFT) {
                    this.renderModels(matrixStackIn, ivertexbuilder, this.leftLid, this.leftLatch, this.leftBottom, f1, i, combinedOverlayIn);
                } else {
                    this.renderModels(matrixStackIn, ivertexbuilder, this.rightLid, this.rightLatch, this.rightBottom, f1, i, combinedOverlayIn);
                }
            } else {
                this.renderModels(matrixStackIn, ivertexbuilder, this.singleLid, this.singleLatch, this.singleBottom, f1, i, combinedOverlayIn);
            }

            matrixStackIn.popPose();
        }
    }

    private void renderModels(PoseStack matrixStackIn, VertexConsumer bufferIn, ModelPart chestLid, ModelPart chestLatch, ModelPart chestBottom, float lidAngle, int combinedLightIn, int combinedOverlayIn) {
        chestLid.xRot = -(lidAngle * ((float)Math.PI / 2F));
        chestLatch.xRot = chestLid.xRot;
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
