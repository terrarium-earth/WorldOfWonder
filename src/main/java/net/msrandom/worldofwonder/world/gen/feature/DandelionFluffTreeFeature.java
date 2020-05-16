package net.msrandom.worldofwonder.world.gen.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.entity.DandeLionEntity;
import net.msrandom.worldofwonder.entity.WonderEntities;

import java.util.Random;

public class DandelionFluffTreeFeature extends WonderTree {
    public DandelionFluffTreeFeature(boolean sapling) {
        super(sapling, WonderBlocks.STEM_WOOD, WonderBlocks.DANDELION_FLUFF);
    }

    @Override
    public boolean place(IWorldGenerationReader world, BlockPos pos, Random rand) {
/*        if (!sapling && world instanceof World) {
            BlockPos entityPos = pos.add(rand.nextInt(16) - 8, 0, rand.nextInt(16) - 8);
            DandeLionEntity entity = WonderEntities.DANDE_LION.create((World) world);
            if (entity != null) {
                entity.setPosition(entityPos.getX(), entityPos.getY(), entityPos.getZ());
                world.addEntity(entity);
            }
        }*/

        int height = rand.nextInt(3) + 6;
        int flags = sapling ? 18 : 3;

        world.removeBlock(pos, false);

        for (int i = 0; i < height - 1; i++) {
            world.setBlockState(pos.up(i), log, flags);
        }

        pos = pos.up(height);

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    world.setBlockState(pos.add(i, j, k), leaves, flags);
                }
            }
        }

        world.removeBlock(pos.add(-1, -1, -1), false);
        world.removeBlock(pos.add(1, -1, 1), false);
        world.removeBlock(pos.add(-1, -1, 1), false);
        world.removeBlock(pos.add(1, -1, -1), false);
        world.removeBlock(pos.add(-1, 1, -1), false);
        world.removeBlock(pos.add(1, 1, 1), false);
        world.removeBlock(pos.add(-1, 1, 1), false);
        world.removeBlock(pos.add(1, 1, -1), false);

        setLeaves(world, pos.add(-2, -1, -2), rand, flags);
        setLeaves(world, pos.add(2, -1, -2), rand, flags);
        setLeaves(world, pos.add(-2, -1, 2), rand, flags);
        setLeaves(world, pos.add(2, -1, 2), rand, flags);
        setLeaves(world, pos.add(-2, 1, -2), rand, flags);
        setLeaves(world, pos.add(2, 1, -2), rand, flags);
        setLeaves(world, pos.add(-2, 1, 2), rand, flags);
        setLeaves(world, pos.add(2, 1, 2), rand, flags);

        setLeaves(world, pos.add(0, -2, 2), rand, flags);
        setLeaves(world, pos.add(0, -2, -2), rand, flags);
        setLeaves(world, pos.add(2, -2, 0), rand, flags);
        setLeaves(world, pos.add(-2, -2, 0), rand, flags);

        setLeaves(world, pos.south(2), rand, flags);
        setLeaves(world, pos.north(2), rand, flags);
        setLeaves(world, pos.east(2), rand, flags);
        setLeaves(world, pos.west(2), rand, flags);

        setLeaves(world, pos.add(0, 2, 2), rand, flags);
        setLeaves(world, pos.add(0, 2, -2), rand, flags);
        setLeaves(world, pos.add(2, 2, 0), rand, flags);
        setLeaves(world, pos.add(-2, 2, 0), rand, flags);

        world.setBlockState(pos.up(2), leaves, flags);
        world.setBlockState(pos.down(), log, flags);

        return true;
    }

    private void setLeaves(IWorldGenerationReader world, BlockPos pos, Random rand, int flags) {
        if (rand.nextInt(4) != 0) {
            world.setBlockState(pos, leaves, flags);
        }
    }
}
