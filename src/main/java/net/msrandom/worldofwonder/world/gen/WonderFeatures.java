package net.msrandom.worldofwonder.world.gen;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.world.gen.foliage.DandelionFluffFoliagePlacer;
import net.msrandom.worldofwonder.world.gen.foliage.DandelionFoliagePlacer;

public class WonderFeatures {
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DANDELION = register("dandelion",
            Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(WonderBlocks.STEM_LOG.get().defaultBlockState()),
                    new SimpleBlockStateProvider(WonderBlocks.DANDELION_PETALS.get().defaultBlockState()),
                    new DandelionFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(0)),
                    new StraightTrunkPlacer(4, 2, 0),
                    new TwoLayerFeature(1, 0, 1)
            ).ignoreVines().build()));

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DANDELION_FLUFF = register("dandelion_fluff",
            Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(WonderBlocks.STEM_LOG.get().defaultBlockState()),
                    new SimpleBlockStateProvider(WonderBlocks.DANDELION_FLUFF.get().defaultBlockState()),
                    new DandelionFluffFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0)),
                    new StraightTrunkPlacer(6, 2, 0),
                    new TwoLayerFeature(1, 0, 1)
            ).ignoreVines().build()));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(WorldOfWonder.MOD_ID, key), configuredFeature);
    }
}
