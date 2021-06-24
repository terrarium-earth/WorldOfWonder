package net.msrandom.worldofwonder.world.gen.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class DandelionFluffFoliagePlacer extends FoliagePlacer {
    public static final Codec<DandelionFluffFoliagePlacer> CODEC = RecordCodecBuilder.create(placerInstance -> foliagePlacerParts(placerInstance).apply(placerInstance, DandelionFluffFoliagePlacer::new));

    public DandelionFluffFoliagePlacer(FeatureSpread radius, FeatureSpread offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return WonderFoliagePlacers.DANDELION_FLUFF.get();
    }

    @Override
    protected void createFoliage(IWorldGenerationReader world, Random random, BaseTreeFeatureConfig config, int p_230372_4_, Foliage p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> leafPositions, int p_230372_9_, MutableBoundingBox box) {
        placeLeavesRow(world, random, config, p_230372_5_.foliagePos(), p_230372_4_, leafPositions, 0, false, box);
        BlockPos pos = p_230372_5_.foliagePos();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    setLeaves(world, box, config, leafPositions, pos.offset(i, j, k), random, false);
                }
            }
        }

        removeLeaves(world, leafPositions, pos.offset(-1, -1, -1));
        removeLeaves(world, leafPositions, pos.offset(1, -1, 1));
        removeLeaves(world, leafPositions, pos.offset(-1, -1, 1));
        removeLeaves(world, leafPositions, pos.offset(1, -1, -1));
        removeLeaves(world, leafPositions, pos.offset(-1, 1, -1));
        removeLeaves(world, leafPositions, pos.offset(1, 1, 1));
        removeLeaves(world, leafPositions, pos.offset(-1, 1, 1));
        removeLeaves(world, leafPositions, pos.offset(1, 1, -1));

        setLeaves(world, box, config, leafPositions, pos.offset(-2, -1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.offset(2, -1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.offset(-2, -1, 2), random);
        setLeaves(world, box, config, leafPositions, pos.offset(2, -1, 2), random);
        setLeaves(world, box, config, leafPositions, pos.offset(-2, 1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.offset(2, 1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.offset(-2, 1, 2), random);
        setLeaves(world, box, config, leafPositions, pos.offset(2, 1, 2), random);

        setLeaves(world, box, config, leafPositions, pos.offset(0, -2, 2), random);
        setLeaves(world, box, config, leafPositions, pos.offset(0, -2, -2), random);
        setLeaves(world, box, config, leafPositions, pos.offset(2, -2, 0), random);
        setLeaves(world, box, config, leafPositions, pos.offset(-2, -2, 0), random);

        setLeaves(world, box, config, leafPositions, pos.south(2), random);
        setLeaves(world, box, config, leafPositions, pos.north(2), random);
        setLeaves(world, box, config, leafPositions, pos.east(2), random);
        setLeaves(world, box, config, leafPositions, pos.west(2), random);

        setLeaves(world, box, config, leafPositions, pos.offset(0, 2, 2), random);
        setLeaves(world, box, config, leafPositions, pos.offset(0, 2, -2), random);
        setLeaves(world, box, config, leafPositions, pos.offset(2, 2, 0), random);
        setLeaves(world, box, config, leafPositions, pos.offset(-2, 2, 0), random);
    }

    private void setLeaves(IWorldGenerationReader world, MutableBoundingBox box, BaseTreeFeatureConfig config, Set<BlockPos> leafPositions, BlockPos pos, Random random) {
        setLeaves(world, box, config, leafPositions, pos, random, true);
    }

    private void setLeaves(IWorldGenerationReader world, MutableBoundingBox box, BaseTreeFeatureConfig config, Set<BlockPos> leafPositions, BlockPos pos, Random random, boolean decay) {
        if (!decay || random.nextInt(6) != 0) {
            world.setBlock(pos, config.leavesProvider.getState(random, pos), 19);
            box.expand(new MutableBoundingBox(pos, pos));
            leafPositions.add(pos);
        }
    }

    private void removeLeaves(IWorldGenerationReader world, Set<BlockPos> leafPositions, BlockPos pos) {
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 19);
        leafPositions.remove(pos);
    }

    @Override
    public int foliageHeight(Random random, int height, BaseTreeFeatureConfig config) {
        return height - 1;
    }

    @Override
    protected boolean shouldSkipLocation(Random random, int x, int y, int z, int radius, boolean doubleTrunk) {
        return false;
    }
}
