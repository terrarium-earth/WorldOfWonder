package net.msrandom.worldofwonder.tileentity;

import net.msrandom.worldofwonder.compat.WonderQuarkCompat;

public class StemTrappedChestTileEntity extends StemChestTileEntity {
   public StemTrappedChestTileEntity() {
      super(WonderQuarkCompat.STEM_TRAPPED_CHEST_ENTITY);
   }

    @Override
    public boolean isTrapped() {
        return true;
    }

    protected void onOpenOrClose() {
      super.onOpenOrClose();
      this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockState().getBlock());
   }
}
