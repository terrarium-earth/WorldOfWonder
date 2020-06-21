package net.msrandom.worldofwonder.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.world.gen.feature.DandelionFluffTreeFeature;
import net.msrandom.worldofwonder.world.gen.feature.DandelionTreeFeature;
import net.msrandom.worldofwonder.world.gen.feature.WonderTree;

import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(modid = WorldOfWonder.MOD_ID)
public class ItemEvents {
    public static final IDispenseItemBehavior BLOOM_MEAL_DISPENSE = new BloomMealDispenseBehavior();
    private static final WonderTree DANDELION_TREE = new DandelionTreeFeature(true);
    private static final WonderTree FLUFF_TREE = new DandelionFluffTreeFeature(true);
    private static final Map<Block, Block> BLOCK_STRIPPING_MAP = new ImmutableMap.Builder<Block, Block>().put(WonderBlocks.STEM_LOG, WonderBlocks.STRIPPED_STEM_LOG).put(WonderBlocks.STEM_WOOD, WonderBlocks.STRIPPED_STEM_WOOD).build();

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);

        if (stack.getItem() instanceof AxeItem) {
            Block stripped = BLOCK_STRIPPING_MAP.get(state.getBlock());
            if (stripped != null) {
                PlayerEntity player = event.getPlayer();
                world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isRemote) {
                    world.setBlockState(pos, stripped.getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)), 11);
                    stack.damageItem(1, player, entity -> entity.sendBreakAnimation(event.getHand()));
                    event.setUseItem(Event.Result.DENY);
                }
            }
        }
        else handleBloomMeal(world, stack, pos, event.getPlayer());
    }

    public static boolean handleBloomMeal(World world, ItemStack stack, BlockPos pos, PlayerEntity player) {
        if (stack.getItem() == WonderItems.BLOOM_MEAL && world.getBlockState(pos).getBlock() == Blocks.DANDELION) {
            if (!world.isRemote) {
                Random rand = world.rand;
                world.playEvent(2005, pos, 0);
                if (!player.abilities.isCreativeMode) stack.shrink(1);
                if (rand.nextInt(3) == 0) {
                    (rand.nextInt(4) == 0 ? FLUFF_TREE : DANDELION_TREE).place(world, pos, rand);
                }
                return true;
            }
        }
        return false;
    }

    private static class BloomMealDispenseBehavior extends OptionalDispenseBehavior {
        protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            this.successful = true;
            World world = source.getWorld();
            BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
            if (!world.isRemote) {
                if (!handleBloomMeal(world, stack, blockpos, FakePlayerFactory.getMinecraft((ServerWorld) world))) {
                    this.successful = false;
                } else {
                    world.playEvent(2005, blockpos, 0);
                }
            }

            return stack;
        }
    }
}
