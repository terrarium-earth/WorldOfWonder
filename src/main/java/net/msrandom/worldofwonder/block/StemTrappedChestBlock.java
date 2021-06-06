package net.msrandom.worldofwonder.block;

import net.minecraft.block.BlockState;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.msrandom.worldofwonder.compat.WonderQuarkCompat;

public class StemTrappedChestBlock extends StemChestBlock {
   public StemTrappedChestBlock() {
      super(() -> WonderQuarkCompat.STEM_TRAPPED_CHEST_ENTITY);
   }

   protected Stat<ResourceLocation> getOpenChestStat() {
      return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
   }

   public boolean isSignalSource(BlockState state) {
      return true;
   }

   public int getSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      return MathHelper.clamp(ChestTileEntity.getOpenCount(blockAccess, pos), 0, 15);
   }

   @Override
   public int getDirectSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      return side == Direction.UP ? blockState.getSignal(blockAccess, pos, side) : 0;
   }
}
