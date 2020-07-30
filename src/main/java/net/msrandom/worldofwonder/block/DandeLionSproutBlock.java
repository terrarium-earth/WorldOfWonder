package net.msrandom.worldofwonder.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.msrandom.worldofwonder.tileentity.WonderTileEntities;

import javax.annotation.Nullable;

public class DandeLionSproutBlock extends ContainerBlock {
    private static final VoxelShape SHAPE = VoxelShapes.create(0.25, 0, 0.25, 0.75, 0.5, 0.75);

    public DandeLionSproutBlock() {
        super(Properties.create(Material.PLANTS).notSolid().sound(SoundType.PLANT).doesNotBlockMovement());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return WonderTileEntities.DANDE_LION_SPROUT.create();
    }
}
