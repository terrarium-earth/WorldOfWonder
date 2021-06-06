package net.msrandom.worldofwonder.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.Constants;
import net.msrandom.worldofwonder.tileentity.WonderTileEntities;

import javax.annotation.Nullable;

public class DandeLionSproutBlock extends ContainerBlock implements IPlantable {
    private static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    private static final VoxelShape SHAPE = VoxelShapes.box(0.25, 0, 0.25, 0.75, 0.5, 0.75);

    public DandeLionSproutBlock() {
        super(Properties.of(Material.PLANT).noOcclusion().sound(SoundType.GRASS).noCollission());
        registerDefaultState(defaultBlockState().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (placer != null) worldIn.setBlock(pos, state.setValue(AXIS, placer.getDirection().getOpposite().getAxis()), Constants.BlockFlags.NOTIFY_NEIGHBORS | Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if (state.getBlock() == this) return worldIn.getBlockState(blockpos).canSustainPlant(worldIn, blockpos, Direction.UP, this);

        Block block = worldIn.getBlockState(blockpos).getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() != this) return defaultBlockState();
        return state;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return WonderTileEntities.DANDE_LION_SPROUT.create();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}
