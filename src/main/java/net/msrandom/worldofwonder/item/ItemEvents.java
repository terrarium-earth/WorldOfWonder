package net.msrandom.worldofwonder.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
    private static final WonderTree DANDELION_TREE = new DandelionTreeFeature(true);
    private static final WonderTree FLUFF_TREE = new DandelionFluffTreeFeature(true);
    private static final Map<Block, Block> BLOCK_STRIPPING_MAP = new ImmutableMap.Builder<Block, Block>().put(WonderBlocks.STEM_LOG, WonderBlocks.STRIPPED_STEM_LOG).put(WonderBlocks.STEM_WOOD, WonderBlocks.STRIPPED_STEM_WOOD).build();

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (item instanceof AxeItem) {
            PlayerEntity playerentity = event.getPlayer();
            world.playSound(playerentity, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isRemote) {
                Block stripped = BLOCK_STRIPPING_MAP.get(block);
                if (stripped != null) {
                    world.setBlockState(pos, stripped.getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)), 11);
                    if (playerentity != null) {
                        stack.damageItem(1, playerentity, player -> player.sendBreakAnimation(event.getHand()));
                    }
                    event.setUseItem(Event.Result.DENY);
                }
            }
        } else if (item == WonderItems.BLOOM_MEAL && block == Blocks.DANDELION) {
            if (!world.isRemote) {
                Random rand = event.getWorld().rand;
                world.playEvent(2005, pos, 0);
                if (rand.nextInt(3) == 0) {
                    (rand.nextInt(4) == 0 ? FLUFF_TREE : DANDELION_TREE).place(world, pos, rand);
                }
            }
        }
    }
}
