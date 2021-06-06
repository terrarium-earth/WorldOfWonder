package net.msrandom.worldofwonder.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class StemStandingSignBlock extends AbstractStemSignBlock {
   public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

   public StemStandingSignBlock() {
      super(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD));
      this.registerDefaultState(this.getStateDefinition().any().setValue(ROTATION, 0).setValue(WATERLOGGED, Boolean.FALSE));
   }

   public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
      return this.defaultBlockState().setValue(ROTATION, MathHelper.floor((double) ((180.0F + context.getRotation()) * 16.0F / 360.0F) + 0.5D) & 15).setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
   }

   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      return facing == Direction.DOWN && !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return state.setValue(ROTATION, rot.rotate(state.getValue(ROTATION), 16));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.setValue(ROTATION, mirrorIn.mirror(state.getValue(ROTATION), 16));
   }

   protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(ROTATION, WATERLOGGED);
   }
}
