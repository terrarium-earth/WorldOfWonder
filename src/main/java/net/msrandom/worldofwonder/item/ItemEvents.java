package net.msrandom.worldofwonder.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.block.trees.Tree;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.block.trees.DandelionFluffTree;
import net.msrandom.worldofwonder.block.trees.DandelionTree;

import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(modid = WorldOfWonder.MOD_ID)
public class ItemEvents {
    public static final IDispenseItemBehavior BLOOM_MEAL_DISPENSE = new BloomMealDispenseBehavior();
    private static final Tree DANDELION_TREE = new DandelionTree();
    private static final Tree FLUFF_TREE = new DandelionFluffTree();
    private static final Map<Block, Block> BLOCK_STRIPPING_MAP = new ImmutableMap.Builder<Block, Block>().put(WonderBlocks.STEM_LOG, WonderBlocks.STRIPPED_STEM_LOG).put(WonderBlocks.STEM_WOOD, WonderBlocks.STRIPPED_STEM_WOOD).build();

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickBlock event) {
        World level = event.getWorld();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (stack.getItem() instanceof AxeItem) {
            Block stripped = BLOCK_STRIPPING_MAP.get(state.getBlock());
            if (stripped != null) {
                PlayerEntity player = event.getPlayer();
                level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide) {
                    level.setBlock(pos, stripped.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 11);
                    stack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(event.getHand()));
                    event.setUseItem(Event.Result.DENY);
                }
            }
        } else if (state.getBlock() == Blocks.FLOWER_POT && stack.getItem() == WonderBlocks.DANDE_LION_SPROUT.asItem()) {
            level.setBlock(pos, WonderBlocks.POTTED_DANDE_LION_SPROUT.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, event.getPlayer().getDirection().getOpposite().getAxis()), Constants.BlockFlags.NOTIFY_NEIGHBORS | Constants.BlockFlags.BLOCK_UPDATE);
            event.getPlayer().awardStat(Stats.POT_FLOWER);
            if (!event.getPlayer().abilities.instabuild) {
                stack.shrink(1);
            }
            event.setUseBlock(Event.Result.DENY);
            event.setCancellationResult(ActionResultType.SUCCESS);
            event.setCanceled(true);
        } else handleBloomMeal(level, stack, pos, event.getPlayer());
    }

    public static boolean handleBloomMeal(World level, ItemStack stack, BlockPos pos, PlayerEntity player) {
        BlockState state = level.getBlockState(pos);
        if (stack.getItem() == WonderItems.BLOOM_MEAL && state.getBlock() == Blocks.DANDELION) {
            if (!level.isClientSide) {
                Random random = level.random;
                level.levelEvent(2005, pos, 0);
                if (!player.abilities.instabuild) stack.shrink(1);
                if (random.nextInt(3) == 0) {
                    (random.nextInt(4) == 0 ? FLUFF_TREE : DANDELION_TREE).growTree((ServerWorld) level, ((ServerWorld) level).getChunkSource().getGenerator(), pos, state, random);
                }
                return true;
            }
        }
        return false;
    }

    private static class BloomMealDispenseBehavior extends OptionalDispenseBehavior {
        @Override
        protected ItemStack execute(IBlockSource source, ItemStack stack) {
            this.setSuccess(true);
            ServerWorld level = source.getLevel();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            if (!level.isClientSide) {
                if (!handleBloomMeal(level, stack, blockpos, FakePlayerFactory.getMinecraft(level))) {
                    this.setSuccess(false);
                } else {
                    level.levelEvent(2005, blockpos, 0);
                }
            }

            return stack;
        }
    }
}
