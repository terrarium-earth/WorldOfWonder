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
        for (int i = 0; i < 4; i++) {
            placeLeavesRow(world, random, config, p_230372_5_.foliagePos(), 2, leafPositions, i, p_230372_5_.doubleTrunk(), box);
        }
    }

    @Override
    public int foliageHeight(Random random, int height, BaseTreeFeatureConfig config) {
        return height - 1;
    }

    @Override
    protected boolean shouldSkipLocation(Random random, int x, int y, int z, int radius, boolean doubleTrunk) {
        boolean skip = true;
        boolean potentialDecay = false;
        int layer = 2 - Math.abs(y - 2);
        switch (layer) {
            case 0: {
                //First and last layer, simply a block on each side
                if (x == 0 && x == z) {
                    skip = false;
                    break;
                }
                skip = isNotCenterEdge(x, z, 2);
                potentialDecay = true;
                break;
            }
            case 1: {
                //2nd layer, edges should spawn, so if both x and z are equal to 2, additionally if both are 0, we allow the center to spawn
                int absX = Math.abs(x);
                if ((absX == 2 || x == 0) && absX == Math.abs(z)) {
                    skip = false;
                    break;
                }
                skip = isNotCenterEdge(x, z, 1);
                break;
            }
            case 2: {
                int distance = x * x + z * z;
                skip = distance > 4; //The center layer, simply a star shape so we just if the distance from center is too far
                potentialDecay = distance > 3;
                break;
            }
        }
        if (!skip && potentialDecay) {
            skip = random.nextInt(6) == 0;
        }
        return skip;
    }

    private boolean isNotCenterEdge(int x, int z, int distance) {
        if (x == 0) {
            return Math.abs(z) != distance;
        } else if (z == 0) {
            return Math.abs(x) != distance;
        }
        return true;
    }
}
