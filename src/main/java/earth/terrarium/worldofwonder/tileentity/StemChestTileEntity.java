package earth.terrarium.worldofwonder.tileentity;

import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import earth.terrarium.worldofwonder.compat.WonderQuarkCompat;

public class StemChestTileEntity extends ChestBlockEntity {
   public StemChestTileEntity() {
      this(WonderQuarkCompat.STEM_CHEST_ENTITY.get());
   }

   public StemChestTileEntity(BlockEntityType<?> type) {
      super(type);
   }

    public boolean isTrapped() {
       return false;
    }
}