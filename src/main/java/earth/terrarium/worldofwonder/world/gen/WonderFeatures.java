package earth.terrarium.worldofwonder.world.gen;

import earth.terrarium.worldofwonder.WorldOfWonder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import earth.terrarium.worldofwonder.block.WonderBlocks;
import earth.terrarium.worldofwonder.world.gen.foliage.DandelionFluffFoliagePlacer;
import earth.terrarium.worldofwonder.world.gen.foliage.DandelionFoliagePlacer;

import net.minecraft.util.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;

public class WonderFeatures {
    public static final ConfiguredFeature<TreeConfiguration, ?> DANDELION = register("dandelion",
            Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    new SimpleStateProvider(WonderBlocks.STEM_LOG.get().defaultBlockState()),
                    new SimpleStateProvider(WonderBlocks.DANDELION_PETALS.get().defaultBlockState()),
                    new DandelionFoliagePlacer(UniformInt.fixed(3), UniformInt.fixed(0)),
                    new StraightTrunkPlacer(4, 2, 0),
                    new TwoLayersFeatureSize(1, 0, 1)
            ).ignoreVines().build()));

    public static final ConfiguredFeature<TreeConfiguration, ?> DANDELION_FLUFF = register("dandelion_fluff",
            Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    new SimpleStateProvider(WonderBlocks.STEM_LOG.get().defaultBlockState()),
                    new SimpleStateProvider(WonderBlocks.DANDELION_FLUFF.get().defaultBlockState()),
                    new DandelionFluffFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(0)),
                    new StraightTrunkPlacer(6, 2, 0),
                    new TwoLayersFeatureSize(1, 0, 1)
            ).ignoreVines().build()));

    private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(WorldOfWonder.MOD_ID, key), configuredFeature);
    }
}
