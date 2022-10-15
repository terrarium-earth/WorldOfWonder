package earth.terrarium.worldofwonder.block;

import earth.terrarium.worldofwonder.tileentity.StemSignTileEntity;
import earth.terrarium.worldofwonder.tileentity.WonderTileEntities;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AbstractStemSignBlock extends SignBlock {
    @SuppressWarnings("ConstantConditions")
    protected AbstractStemSignBlock(Properties propertiesIn) {
        super(propertiesIn, null);
    }

    public BlockEntity newBlockEntity(BlockGetter worldIn) {
        return WonderTileEntities.STEM_SIGN.get().create();
    }

    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);
        boolean flag = itemstack.getItem() instanceof DyeItem && player.abilities.mayBuild;
        if (worldIn.isClientSide) {
            return flag ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        } else {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof StemSignTileEntity) {
                StemSignTileEntity sign = (StemSignTileEntity)tileentity;
                if (flag) {
                    boolean flag1 = sign.setTextColor(((DyeItem)itemstack.getItem()).getDyeColor());
                    if (flag1 && !player.isCreative()) {
                        itemstack.shrink(1);
                    }
                }

                return sign.executeCommand(player) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            } else {
                return InteractionResult.PASS;
            }
        }
    }
}
