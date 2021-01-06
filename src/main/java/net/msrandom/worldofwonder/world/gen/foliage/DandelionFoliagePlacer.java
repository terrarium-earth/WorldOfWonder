package net.msrandom.worldofwonder.world.gen.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class DandelionFoliagePlacer extends FoliagePlacer {
    public static final Codec<DandelionFoliagePlacer> CODEC = RecordCodecBuilder.create(placerInstance -> func_242830_b(placerInstance).apply(placerInstance, DandelionFoliagePlacer::new));

    public DandelionFoliagePlacer(FeatureSpread radius, FeatureSpread offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> func_230371_a_() {
        return WonderFoliagePlacers.DANDELION;
    }

    @Override
    protected void func_230372_a_(IWorldGenerationReader world, Random random, BaseTreeFeatureConfig config, int p_230372_4_, Foliage p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> leafPositions, int p_230372_9_, MutableBoundingBox box) {
        BlockPos pos = p_230372_5_.func_236763_a_();
        for (int i = -1; i <= 1; i++) setLeaves(world, box, config, leafPositions, pos.add(2, 0, i), random, false);
        for (int i = -1; i <= 1; i++) setLeaves(world, box, config, leafPositions, pos.add(-2, 0, i), random, false);
        for (int i = -1; i <= 1; i++) setLeaves(world, box, config, leafPositions, pos.add(i, 0, 2), random, false);
        for (int i = -1; i <= 1; i++) setLeaves(world, box, config, leafPositions, pos.add(i, 0, -2), random, false);

        setLeaves(world, box, config, leafPositions, pos.north(3), random);
        setLeaves(world, box, config, leafPositions, pos.south(3), random);
        setLeaves(world, box, config, leafPositions, pos.east(3), random);
        setLeaves(world, box, config, leafPositions, pos.west(3), random);

        setLeaves(world, box, config, leafPositions, pos.up(2), random, false);
        setLeaves(world, box, config, leafPositions, pos.add(0, 2, 1), random);
        setLeaves(world, box, config, leafPositions, pos.add(0, 2, -1), random);
        setLeaves(world, box, config, leafPositions, pos.add(1, 2, 0), random);
        setLeaves(world, box, config, leafPositions, pos.add(-1, 2, 0), random);

        setLeaves(world, box, config, leafPositions, pos.add(-2, 1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(-2, 1, 2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, 1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, 1, 2), random);
        setLeaves(world, box, config, leafPositions, pos.add(-2, -1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(-2, -1, 2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, -1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, -1, 2), random);

        setLeaves(world, box, config, leafPositions, pos.add(0, 1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(0, 1, 2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, 1, 0), random);
        setLeaves(world, box, config, leafPositions, pos.add(-2, 1, 0), random);
        setLeaves(world, box, config, leafPositions, pos.add(0, -1, -2), random);
        setLeaves(world, box, config, leafPositions, pos.add(0, -1, 2), random);
        setLeaves(world, box, config, leafPositions, pos.add(2, -1, 0), random);
        setLeaves(world, box, config, leafPositions, pos.add(-2, -1, 0), random);
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

    @Override
    public int func_230374_a_(Random p_230374_1_, int p_230374_2_, BaseTreeFeatureConfig p_230374_3_) {
        return p_230374_2_ - 1;
    }

    @Override
    protected boolean func_230373_a_(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
        return false;
    }
}
