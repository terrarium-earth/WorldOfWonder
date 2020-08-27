package net.msrandom.worldofwonder.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.network.OpenStemSignPacket;
import net.msrandom.worldofwonder.tileentity.StemSignTileEntity;

import javax.annotation.Nullable;

public class StemSignItem extends WallOrFloorItem {
   public StemSignItem(Block floorBlockIn, Item.Properties propertiesIn) {
      super(floorBlockIn, WonderBlocks.STEM_WALL_SIGN, propertiesIn);
   }

   protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
      boolean flag = super.onBlockPlaced(pos, worldIn, player, stack, state);

      if (!worldIn.isRemote && !flag && player != null) {
          TileEntity tileEntity = worldIn.getTileEntity(pos);
          if (tileEntity instanceof StemSignTileEntity) {
              ((StemSignTileEntity) tileEntity).setPlayer(player);
              WorldOfWonder.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new OpenStemSignPacket(pos));
          }
      }

      return flag;
   }
}
