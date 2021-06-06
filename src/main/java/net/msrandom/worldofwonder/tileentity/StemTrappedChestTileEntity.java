package net.msrandom.worldofwonder.tileentity;

import net.msrandom.worldofwonder.compat.WonderQuarkCompat;

public class StemTrappedChestTileEntity extends StemChestTileEntity {
   public StemTrappedChestTileEntity() {
      super(WonderQuarkCompat.STEM_TRAPPED_CHEST_ENTITY.get());
   }

    @Override
    public boolean isTrapped() {
        return true;
    }

    protected void signalOpenCount() {
      super.signalOpenCount();
      this.level.updateNeighborsAt(this.worldPosition.below(), this.getBlockState().getBlock());
   }
}
