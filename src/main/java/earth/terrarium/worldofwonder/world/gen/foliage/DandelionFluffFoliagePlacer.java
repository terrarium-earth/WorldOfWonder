package earth.terrarium.worldofwonder.world.gen.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;

public class DandelionFluffFoliagePlacer extends FoliagePlacer {
    public static final Codec<DandelionFluffFoliagePlacer> CODEC = RecordCodecBuilder.create(placerInstance -> foliagePlacerParts(placerInstance).apply(placerInstance, DandelionFluffFoliagePlacer::new));

    public DandelionFluffFoliagePlacer(UniformInt radius, UniformInt offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return WonderFoliagePlacers.DANDELION_FLUFF.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedRW world, Random random, TreeConfiguration config, int p_230372_4_, FoliageAttachment p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> leafPositions, int p_230372_9_, BoundingBox box) {
        BlockPos position = p_230372_5_.foliagePos().below();
        for (int i = 0; i < 5; i++) {
            placeLeavesRow(world, random, config, position, 2, leafPositions, i, p_230372_5_.doubleTrunk(), box);
        }
    }

    @Override
    public int foliageHeight(Random random, int height, TreeConfiguration config) {
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
                if (y != 0 && x == 0 && z == 0) {
                    skip = false;
                    break;
                }
                skip = isNotCenterEdge(x, z, 2);
                potentialDecay = true;
                break;
            }
            case 1: {
                //2nd and 4th layer, edges should spawn, so if both x and z are equal to 2, additionally if both are 0, we allow the center to spawn
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

    public static boolean isNotCenterEdge(int x, int z, int distance) {
        if (x == 0) {
            return Math.abs(z) != distance;
        } else if (z == 0) {
            return Math.abs(x) != distance;
        }
        return true;
    }
}
