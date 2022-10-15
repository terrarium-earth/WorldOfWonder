package earth.terrarium.worldofwonder.world.gen;

import earth.terrarium.worldofwonder.block.WonderBlocks;
import earth.terrarium.worldofwonder.world.gen.foliage.DandelionFluffFoliagePlacer;
import earth.terrarium.worldofwonder.world.gen.foliage.DandelionFoliagePlacer;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class WonderFeatures {
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> DANDELION = register("dandelion",
            Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    new SimpleStateProvider(WonderBlocks.STEM_LOG.get().defaultBlockState()),
                    new SimpleStateProvider(WonderBlocks.DANDELION_PETALS.get().defaultBlockState()),
                    new DandelionFoliagePlacer(UniformInt.of(3, 3), UniformInt.of(0, 0)),
                    new StraightTrunkPlacer(4, 2, 0),
                    new TwoLayersFeatureSize(1, 0, 1)
            ).ignoreVines().build()
    );

    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> DANDELION_FLUFF = register("dandelion_fluff",
            Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    new SimpleStateProvider(WonderBlocks.STEM_LOG.get().defaultBlockState()),
                    new SimpleStateProvider(WonderBlocks.DANDELION_FLUFF.get().defaultBlockState()),
                    new DandelionFluffFoliagePlacer(UniformInt.of(2, 2), UniformInt.of(0, 0)),
                    new StraightTrunkPlacer(6, 2, 0),
                    new TwoLayersFeatureSize(1, 0, 1)
            ).ignoreVines().build()
    );

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String key, F feature, FC configuration) {
        return FeatureUtils.register(key, feature, configuration);
    }
}
