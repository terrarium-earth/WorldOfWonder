package net.msrandom.worldofwonder.world.gen.foliageplacer;

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
    protected void func_230372_a_(IWorldGenerationReader p_230372_1_, Random random, BaseTreeFeatureConfig p_230372_3_, int p_230372_4_, Foliage p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> leafPositions, int p_230372_9_, MutableBoundingBox p_230372_10_) {
        BlockPos pos = p_230372_5_.func_236763_a_();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    leafPositions.add(pos.add(i, j, k));
                }
            }
        }

        leafPositions.remove(pos.add(-1, -1, -1));
        leafPositions.remove(pos.add(1, -1, 1));
        leafPositions.remove(pos.add(-1, -1, 1));
        leafPositions.remove(pos.add(1, -1, -1));
        leafPositions.remove(pos.add(-1, 1, -1));
        leafPositions.remove(pos.add(1, 1, 1));
        leafPositions.remove(pos.add(-1, 1, 1));
        leafPositions.remove(pos.add(1, 1, -1));

        setLeaves(leafPositions, pos.add(-2, -1, -2), random);
        setLeaves(leafPositions, pos.add(2, -1, -2), random);
        setLeaves(leafPositions, pos.add(-2, -1, 2), random);
        setLeaves(leafPositions, pos.add(2, -1, 2), random);
        setLeaves(leafPositions, pos.add(-2, 1, -2), random);
        setLeaves(leafPositions, pos.add(2, 1, -2), random);
        setLeaves(leafPositions, pos.add(-2, 1, 2), random);
        setLeaves(leafPositions, pos.add(2, 1, 2), random);

        setLeaves(leafPositions, pos.add(0, -2, 2), random);
        setLeaves(leafPositions, pos.add(0, -2, -2), random);
        setLeaves(leafPositions, pos.add(2, -2, 0), random);
        setLeaves(leafPositions, pos.add(-2, -2, 0), random);

        setLeaves(leafPositions, pos.south(2), random);
        setLeaves(leafPositions, pos.north(2), random);
        setLeaves(leafPositions, pos.east(2), random);
        setLeaves(leafPositions, pos.west(2), random);

        setLeaves(leafPositions, pos.add(0, 2, 2), random);
        setLeaves(leafPositions, pos.add(0, 2, -2), random);
        setLeaves(leafPositions, pos.add(2, 2, 0), random);
        setLeaves(leafPositions, pos.add(-2, 2, 0), random);
    }

    private void setLeaves(Set<BlockPos> leafPositions, BlockPos pos, Random rand) {
        if (rand.nextInt(6) != 0) {
            leafPositions.add(pos);
        }
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
