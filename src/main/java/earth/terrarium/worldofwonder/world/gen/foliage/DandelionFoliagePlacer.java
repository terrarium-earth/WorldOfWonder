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

public class DandelionFoliagePlacer extends FoliagePlacer {
    public static final Codec<DandelionFoliagePlacer> CODEC = RecordCodecBuilder.create(placerInstance -> foliagePlacerParts(placerInstance).apply(placerInstance, DandelionFoliagePlacer::new));

    public DandelionFoliagePlacer(UniformInt radius, UniformInt offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return WonderFoliagePlacers.DANDELION.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedRW world, Random random, TreeConfiguration config, int p_230372_4_, FoliageAttachment p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> leafPositions, int p_230372_9_, BoundingBox box) {
        for (int i = 0; i < 4; i++) {
            placeLeavesRow(world, random, config, p_230372_5_.foliagePos(), 3, leafPositions, i, p_230372_5_.doubleTrunk(), box);
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
        int distance = x * x + z * z;
        switch (y) {
            case 0:
            case 2: {
                skip = distance > 4;
                potentialDecay = distance > 3;
                break;
            }
            case 1: {
                int distanceX = Math.abs(x);
                int distanceZ = Math.abs(z);
                skip = distance > 9;
                potentialDecay = (distanceX > 1 || distanceZ > 1) && distance > 2;
                if (distanceX == 2 && distanceZ == 2) skip = true;
                break;
            }
            case 3: {
                if (x == 0 && z == 0) {
                    skip = false;
                    break;
                }
                skip = DandelionFluffFoliagePlacer.isNotCenterEdge(x, z, 1);
                break;
            }
        }
        if (!skip && potentialDecay) {
            skip = random.nextInt(6) == 0;
        }
        return skip;
    }
}
