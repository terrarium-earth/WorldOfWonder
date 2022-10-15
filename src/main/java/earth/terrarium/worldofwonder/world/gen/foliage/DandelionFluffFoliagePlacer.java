package earth.terrarium.worldofwonder.world.gen.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.function.BiConsumer;

public class DandelionFluffFoliagePlacer extends FoliagePlacer {
    public static final Codec<DandelionFluffFoliagePlacer> CODEC = RecordCodecBuilder.create(placerInstance -> foliagePlacerParts(placerInstance).apply(placerInstance, DandelionFluffFoliagePlacer::new));

    public DandelionFluffFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return WonderFoliagePlacers.DANDELION_FLUFF.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos position = attachment.pos().below();
        for (int i = 0; i < 5; i++) {
            placeLeavesRow(level, blockSetter, random, config, position, 2, i, attachment.doubleTrunk());
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return height - 1;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        boolean skip = true;
        boolean potentialDecay = false;
        int layer = 2 - Math.abs(localY - 2);
        switch (layer) {
            case 0 -> {
                //First and last layer, simply a block on each side
                if (localY != 0 && localX == 0 && localZ == 0) {
                    skip = false;
                    break;
                }
                skip = isNotCenterEdge(localX, localZ, 2);
                potentialDecay = true;
            }
            case 1 -> {
                //2nd and 4th layer, edges should spawn, so if both x and z are equal to 2, additionally if both are 0, we allow the center to spawn
                int absX = Math.abs(localX);
                if ((absX == 2 || localX == 0) && absX == Math.abs(localZ)) {
                    skip = false;
                    break;
                }
                skip = isNotCenterEdge(localX, localZ, 1);
            }
            case 2 -> {
                int distance = localX * localX + localZ * localZ;
                skip = distance > 4; //The center layer, simply a star shape so we just if the distance from center is too far
                potentialDecay = distance > 3;
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
