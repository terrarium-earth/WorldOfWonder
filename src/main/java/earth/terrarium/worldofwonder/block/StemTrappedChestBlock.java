package earth.terrarium.worldofwonder.block;

import earth.terrarium.worldofwonder.compat.WonderQuarkCompat;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;

public class StemTrappedChestBlock extends StemChestBlock {
   public StemTrappedChestBlock() {
      super(WonderQuarkCompat.STEM_TRAPPED_CHEST_ENTITY::get);
   }

   protected Stat<ResourceLocation> getOpenChestStat() {
      return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
   }

   public boolean isSignalSource(BlockState state) {
      return true;
   }

   public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
      return Mth.clamp(ChestBlockEntity.getOpenCount(blockAccess, pos), 0, 15);
   }

   @Override
   public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
      return side == Direction.UP ? blockState.getSignal(blockAccess, pos, side) : 0;
   }
}
