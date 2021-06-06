package net.msrandom.worldofwonder.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;

public class StemWallSignBlock extends AbstractStemSignBlock {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0.0D, 4.5D, 14.0D, 16.0D, 12.5D, 16.0D), Direction.SOUTH, Block.box(0.0D, 4.5D, 0.0D, 16.0D, 12.5D, 2.0D), Direction.EAST, Block.box(0.0D, 4.5D, 0.0D, 2.0D, 12.5D, 16.0D), Direction.WEST, Block.box(14.0D, 4.5D, 0.0D, 16.0D, 12.5D, 16.0D)));

    public StemWallSignBlock() {
        super(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(() -> WonderBlocks.STEM_SIGN));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public String getDescriptionId() {
        return this.asItem().getDescriptionId();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.relative(state.getValue(FACING).getOpposite())).getMaterial().isSolid();
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = this.defaultBlockState();
        World level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = level.getFluidState(pos);
        Direction[] directions = context.getNearestLookingDirections();

        for (Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                state = state.setValue(FACING, direction1);
                if (state.canSurvive(level, pos)) {
                    return state.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing.getOpposite() == stateIn.getValue(FACING) && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }
}
