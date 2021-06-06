package net.msrandom.worldofwonder.block;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class VerticalSlabBlock extends Block implements IWaterLoggable {
    public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabType.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public VerticalSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(TYPE, VerticalSlabType.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return state.getValue(TYPE) != VerticalSlabType.DOUBLE;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.getValue(TYPE).getShape();
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getClickedPos();
        World world = context.getLevel();
        BlockState state = world.getBlockState(pos);
        if(state.getBlock() == this) {
            return state.setValue(TYPE, VerticalSlabType.DOUBLE).setValue(WATERLOGGED, false);
        }
        return this.defaultBlockState().setValue(TYPE, VerticalSlabType.getSlabTypeByDirection(this.calculateDirectionForPlacement(context))).setValue(WATERLOGGED, world.getFluidState(pos).getType().is(FluidTags.WATER));
    }

    protected Direction calculateDirectionForPlacement(BlockItemUseContext context) {
        Direction face = context.getClickedFace();
        if(face.getAxis() != Direction.Axis.Y) {
            return face;
        }
        Vector3d difference = context.getClickLocation().subtract(Vector3d.atCenterOf(context.getClickedPos())).subtract(0.5, 0, 0.5);
        return Direction.fromYRot(-Math.toDegrees(Math.atan2(difference.x(), difference.z()))).getOpposite();
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext context) {
        VerticalSlabType slabtype = state.getValue(TYPE);
        return slabtype != VerticalSlabType.DOUBLE && context.getItemInHand().getItem() == this.asItem() && context.replacingClickedOnBlock() && (context.getClickedFace() == slabtype.slabDirection && this.calculateDirectionForPlacement(context) == slabtype.slabDirection);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public boolean placeLiquid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return state.getValue(TYPE) != VerticalSlabType.DOUBLE && IWaterLoggable.super.placeLiquid(worldIn, pos, state, fluidStateIn);
    }

    @Override
    public boolean canPlaceLiquid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return state.getValue(TYPE) != VerticalSlabType.DOUBLE && IWaterLoggable.super.canPlaceLiquid(worldIn, pos, state, fluidIn);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(state.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return state;
    }

    @Override
    public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return type == PathType.WATER && worldIn.getFluidState(pos).is(FluidTags.WATER);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 20;
    }

    public enum VerticalSlabType implements IStringSerializable {
        NORTH(Direction.NORTH),
        SOUTH(Direction.SOUTH),
        WEST(Direction.WEST),
        EAST(Direction.EAST),
        DOUBLE(null);

        @Nullable
        public final Direction slabDirection;

        VerticalSlabType(@Nullable Direction slabDirection) {
            this.slabDirection = slabDirection;
        }

        public VoxelShape getShape() {
            if(this.slabDirection != null) {
                double minXZ = this.slabDirection.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? 8 : 0;
                double maxXZ = this.slabDirection.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? 16 : 8;
                return this.slabDirection.getAxis() == Direction.Axis.X ? Block.box(minXZ, 0, 0, maxXZ, 16, 16) : Block.box(0, 0, minXZ, 16, 16, maxXZ);
            }
            return VoxelShapes.block();
        }

        @Nullable
        public static VerticalSlabType getSlabTypeByDirection(@Nullable Direction direction) {
            for(VerticalSlabType types : VerticalSlabType.values()) {
                if(types.slabDirection == direction) {
                    return types;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.slabDirection != null ? this.slabDirection.getSerializedName() : "double";
        }

        @Override
        public String getSerializedName() {
            return this.toString();
        }
    }
}