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
    public static final Codec<DandelionFluffFoliagePlacer> CODEC = RecordCodecBuilder.create(placerInstance -> func_242830_b(placerInstance).apply(placerInstance, DandelionFluffFoliagePlacer::new));

    public DandelionFluffFoliagePlacer(FeatureSpread radius, FeatureSpread offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> func_230371_a_() {
        return WonderFoliagePlacers.DANDELION_FLUFF;
    }

    @Override
    protected void func_230372_a_(IWorldGenerationReader world, Random random, BaseTreeFeatureConfig config, int p_230372_4_, Foliage p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> leafPositions, int p_230372_9_, MutableBoundingBox box) {
        func_236753_a_(world, random, config, p_230372_5_.func_236763_a_(), p_230372_4_, leafPositions, 0, false, box);
        BlockPos pos = p_230372_5_.func_236763_a_();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    setLeaves(world, box, config, leafPositions, pos.add(i, j, k), random, false);
                }
            }
        }

        removeLeaves(world, leafPositions, pos.add(-1, -1, -1));
        removeLeaves(world, leafPositions, pos.add(1, -1, 1));
        removeLeaves(world, leafPositions, pos.add(-1, -1, 1));
        removeLeaves(world, leafPositions, pos.add(1, -1, -1));
        removeLeaves(world, leafPositions, pos.add(-1, 1, -1));
        removeLeaves(world, leafPositions, pos.add(1, 1, 1));
        removeLeaves(world, leafPositions, pos.add(-1, 1, 1));
        removeLeaves(world, leafPositions, pos.add(1, 1, -1));

        setLeaves(world, box, config, leafPositions, pos.add(-2, -1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, -1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(-2, -1, 2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, -1, 2), random);
        setLeaves(world, box, config, leafPositions, pos.add(-2, 1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, 1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(-2, 1, 2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, 1, 2), random);

        setLeaves(world, box, config, leafPositions, pos.add(0, -2, 2), random);
        setLeaves(world, box, config, leafPositions, pos.add(0, -2, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, -2, 0), random);
        setLeaves(world, box, config, leafPositions, pos.add(-2, -2, 0), random);

        setLeaves(world, box, config, leafPositions, pos.south(2), random);
        setLeaves(world, box, config, leafPositions, pos.north(2), random);
        setLeaves(world, box, config, leafPositions, pos.east(2), random);
        setLeaves(world, box, config, leafPositions, pos.west(2), random);

        setLeaves(world, box, config, leafPositions, pos.add(0, 2, 2), random);
        setLeaves(world, box, config, leafPositions, pos.add(0, 2, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, 2, 0), random);
        setLeaves(world, box, config, leafPositions, pos.add(-2, 2, 0), random);
    }

    private void setLeaves(IWorldGenerationReader world, MutableBoundingBox box, BaseTreeFeatureConfig config, Set<BlockPos> leafPositions, BlockPos pos, Random random) {
        setLeaves(world, box, config, leafPositions, pos, random, true);
    }

    private void setLeaves(IWorldGenerationReader world, MutableBoundingBox box, BaseTreeFeatureConfig config, Set<BlockPos> leafPositions, BlockPos pos, Random random, boolean decay) {
        if (!decay || random.nextInt(6) != 0) {
            world.setBlockState(pos, config.leavesProvider.getBlockState(random, pos), 19);
            box.expandTo(new MutableBoundingBox(pos, pos));
            leafPositions.add(pos);
        }
    }

    private void removeLeaves(IWorldGenerationReader world, Set<BlockPos> leafPositions, BlockPos pos) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 19);
        leafPositions.remove(pos);
    }

    @Override
    public int func_230374_a_(Random p_230374_1_, int p_230374_2_, BaseTreeFeatureConfig p_230374_3_) {
        return p_230374_2_ - 1;
    }

    @Override
    protected boolean func_230373_a_(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
        return true;
    }
}
