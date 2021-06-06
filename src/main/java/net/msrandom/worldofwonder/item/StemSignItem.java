package net.msrandom.worldofwonder.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.PacketDistributor;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.network.OpenStemSignPacket;
import net.msrandom.worldofwonder.tileentity.StemSignTileEntity;

public class StemSignItem extends WallOrFloorItem {
   public StemSignItem(Block floorBlockIn, Item.Properties propertiesIn) {
      super(floorBlockIn, WonderBlocks.STEM_WALL_SIGN, propertiesIn);
   }

    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
      boolean flag = super.placeBlock(context, state);

      if (!context.getLevel().isClientSide && !flag && context.getPlayer() != null) {
          TileEntity tileEntity = context.getLevel().getBlockEntity(context.getClickedPos());
          if (tileEntity instanceof StemSignTileEntity) {
              ((StemSignTileEntity) tileEntity).setPlayer(context.getPlayer());
              WorldOfWonder.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) context.getPlayer()), new OpenStemSignPacket(context.getClickedPos()));
          }
      }

      return flag;
   }
}
