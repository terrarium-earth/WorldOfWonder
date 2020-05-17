package net.msrandom.worldofwonder.item;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.world.gen.feature.DandelionFluffTreeFeature;
import net.msrandom.worldofwonder.world.gen.feature.DandelionTreeFeature;
import net.msrandom.worldofwonder.world.gen.feature.WonderTree;

import java.util.Random;

@Mod.EventBusSubscriber(modid = WorldOfWonder.MOD_ID)
public class BloomMealEvents {
    private static final WonderTree DANDELION = new DandelionTreeFeature(true);
    private static final WonderTree FLUFF = new DandelionFluffTreeFeature(true);

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        if (!world.isRemote) {
            BlockPos pos = event.getPos();
            if (event.getItemStack().getItem() == WonderItems.BLOOM_MEAL && world.getBlockState(pos).getBlock() == Blocks.DANDELION) {
                Random rand = event.getWorld().rand;
                world.playEvent(2005, pos, 0);
                if (rand.nextInt(3) == 0) {
                    (rand.nextInt(4) == 0 ? FLUFF : DANDELION).place(world, pos, rand);
                }
            }
        }
    }
}
