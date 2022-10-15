package earth.terrarium.worldofwonder.block;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class VerticalSlabBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabType.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public VerticalSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(TYPE, VerticalSlabType.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return state.getValue(TYPE) != VerticalSlabType.DOUBLE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(TYPE).getShape();
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        BlockState state = world.getBlockState(pos);
        if(state.getBlock() == this) {
            return state.setValue(TYPE, VerticalSlabType.DOUBLE).setValue(WATERLOGGED, false);
        }
        return this.defaultBlockState().setValue(TYPE, VerticalSlabType.getSlabTypeByDirection(this.calculateDirectionForPlacement(context))).setValue(WATERLOGGED, world.getFluidState(pos).getType().is(FluidTags.WATER));
    }

    protected Direction calculateDirectionForPlacement(BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        if(face.getAxis() != Direction.Axis.Y) {
            return face;
        }
        Vec3 difference = context.getClickLocation().subtract(Vec3.atCenterOf(context.getClickedPos())).subtract(0.5, 0, 0.5);
        return Direction.fromYRot(-Math.toDegrees(Math.atan2(difference.x(), difference.z()))).getOpposite();
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        VerticalSlabType slabtype = state.getValue(TYPE);
        return slabtype != VerticalSlabType.DOUBLE && context.getItemInHand().getItem() == this.asItem() && context.replacingClickedOnBlock() && (context.getClickedFace() == slabtype.slabDirection && this.calculateDirectionForPlacement(context) == slabtype.slabDirection);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public boolean placeLiquid(LevelAccessor worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return state.getValue(TYPE) != VerticalSlabType.DOUBLE && SimpleWaterloggedBlock.super.placeLiquid(worldIn, pos, state, fluidStateIn);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return state.getValue(TYPE) != VerticalSlabType.DOUBLE && SimpleWaterloggedBlock.super.canPlaceLiquid(worldIn, pos, state, fluidIn);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(state.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return state;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return type == PathComputationType.WATER && worldIn.getFluidState(pos).is(FluidTags.WATER);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 20;
    }

    public enum VerticalSlabType implements StringRepresentable {
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
            return Shapes.block();
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