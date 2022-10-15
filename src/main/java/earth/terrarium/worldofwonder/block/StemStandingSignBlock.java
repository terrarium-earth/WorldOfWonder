package earth.terrarium.worldofwonder.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

public class StemStandingSignBlock extends AbstractStemSignBlock {
   public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

   public StemStandingSignBlock() {
      super(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD));
      this.registerDefaultState(this.getStateDefinition().any().setValue(ROTATION, 0).setValue(WATERLOGGED, Boolean.FALSE));
   }

   public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
      return this.defaultBlockState().setValue(ROTATION, Mth.floor((double) ((180.0F + context.getRotation()) * 16.0F / 360.0F) + 0.5D) & 15).setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
   }

   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      return facing == Direction.DOWN && !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return state.setValue(ROTATION, rot.rotate(state.getValue(ROTATION), 16));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.setValue(ROTATION, mirrorIn.mirror(state.getValue(ROTATION), 16));
   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(ROTATION, WATERLOGGED);
   }
}
