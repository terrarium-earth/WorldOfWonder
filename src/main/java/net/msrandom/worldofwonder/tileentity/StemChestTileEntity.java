package net.msrandom.worldofwonder.tileentity;

import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.msrandom.worldofwonder.compat.WonderQuarkCompat;

public class StemChestTileEntity extends ChestTileEntity {
   public StemChestTileEntity() {
      this(WonderQuarkCompat.STEM_CHEST_ENTITY);
   }

   public StemChestTileEntity(TileEntityType<?> type) {
      super(type);
   }

    public boolean isTrapped() {
       return false;
    }
}