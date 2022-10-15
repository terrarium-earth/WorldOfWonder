package earth.terrarium.worldofwonder.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import earth.terrarium.worldofwonder.compat.WonderQuarkCompat;
import net.minecraft.world.level.block.state.BlockState;

public class StemChestTileEntity extends ChestBlockEntity {
   public StemChestTileEntity(BlockPos pos, BlockState state) {
      this(WonderQuarkCompat.STEM_CHEST_ENTITY.get(), pos, state);
   }

   public StemChestTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

    public boolean isTrapped() {
       return false;
    }
}
