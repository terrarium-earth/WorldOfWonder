package earth.terrarium.worldofwonder.item;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.network.PacketDistributor;
import earth.terrarium.worldofwonder.WorldOfWonder;
import earth.terrarium.worldofwonder.block.WonderBlocks;
import earth.terrarium.worldofwonder.network.OpenStemSignPacket;
import earth.terrarium.worldofwonder.tileentity.StemSignTileEntity;

public class StemSignItem extends StandingAndWallBlockItem {
   public StemSignItem(Block floorBlockIn, Item.Properties propertiesIn) {
      super(floorBlockIn, WonderBlocks.STEM_WALL_SIGN.get(), propertiesIn);
   }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
      boolean flag = super.placeBlock(context, state);
      if (!context.getLevel().isClientSide && flag && context.getPlayer() != null) {
          BlockEntity tileEntity = context.getLevel().getBlockEntity(context.getClickedPos());
          if (tileEntity instanceof StemSignTileEntity) {
              ((StemSignTileEntity) tileEntity).setPlayer(context.getPlayer());
              WorldOfWonder.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) context.getPlayer()), new OpenStemSignPacket(context.getClickedPos()));
          }
      }

      return flag;
   }
}
