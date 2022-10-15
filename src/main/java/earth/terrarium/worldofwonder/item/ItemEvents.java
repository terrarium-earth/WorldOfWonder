package earth.terrarium.worldofwonder.item;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import earth.terrarium.worldofwonder.WorldOfWonder;
import earth.terrarium.worldofwonder.block.WonderBlocks;
import earth.terrarium.worldofwonder.block.trees.DandelionFluffTree;
import earth.terrarium.worldofwonder.block.trees.DandelionTree;

import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mod.EventBusSubscriber(modid = WorldOfWonder.MOD_ID)
public class ItemEvents {
    public static final DispenseItemBehavior BLOOM_MEAL_DISPENSE = new BloomMealDispenseBehavior();
    private static final AbstractTreeGrower DANDELION_TREE = new DandelionTree();
    private static final AbstractTreeGrower FLUFF_TREE = new DandelionFluffTree();

    @SubscribeEvent
    public static void onToolUse(BlockEvent.BlockToolInteractEvent event) {
        LevelAccessor level = event.getWorld();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (event.getToolType() == ToolType.AXE) {
            Block stripped = null;
            if (state.getBlock() == WonderBlocks.STEM_LOG.get()) stripped = WonderBlocks.STRIPPED_STEM_LOG.get();
            else if (state.getBlock() == WonderBlocks.STEM_WOOD.get()) stripped = WonderBlocks.STRIPPED_STEM_WOOD.get();
            if (stripped != null) {
                Player player = event.getPlayer();
                level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide()) {
                    event.setFinalState(stripped.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() == Blocks.FLOWER_POT && stack.getItem() == WonderBlocks.DANDE_LION_SPROUT.get().asItem()) {
            level.setBlock(pos, WonderBlocks.POTTED_DANDE_LION_SPROUT.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, event.getPlayer().getDirection().getOpposite().getAxis()), Constants.BlockFlags.NOTIFY_NEIGHBORS | Constants.BlockFlags.BLOCK_UPDATE);
            event.getPlayer().awardStat(Stats.POT_FLOWER);
            if (!event.getPlayer().abilities.instabuild) {
                stack.shrink(1);
            }
            event.setUseBlock(Event.Result.DENY);
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        } else {
            handleBloomMeal(level, stack, pos, event.getPlayer());
        }
    }

    public static boolean handleBloomMeal(Level level, ItemStack stack, BlockPos pos, Player player) {
        BlockState state = level.getBlockState(pos);
        if (stack.getItem() == WonderItems.BLOOM_MEAL.get() && state.getBlock() == Blocks.DANDELION) {
            if (!level.isClientSide) {
                Random random = level.random;
                level.levelEvent(2005, pos, 0);
                if (!player.abilities.instabuild) stack.shrink(1);
                if (random.nextInt(3) == 0) {
                    (random.nextInt(4) == 0 ? FLUFF_TREE : DANDELION_TREE).growTree((ServerLevel) level, ((ServerLevel) level).getChunkSource().getGenerator(), pos, state, random);
                }
                return true;
            }
        }
        return false;
    }

    private static class BloomMealDispenseBehavior extends OptionalDispenseItemBehavior {
        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            this.setSuccess(true);
            ServerLevel level = source.getLevel();
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
