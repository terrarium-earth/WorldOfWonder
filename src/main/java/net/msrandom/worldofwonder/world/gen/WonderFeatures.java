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
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DANDELION = register("dandelion", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WonderBlocks.STEM_LOG.getDefaultState()), new SimpleBlockStateProvider(WonderBlocks.DANDELION_PETALS.getDefaultState()), new DandelionFoliagePlacer(FeatureSpread.func_242252_a(3), FeatureSpread.func_242252_a(0)), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DANDELION_FLUFF = register("dandelion_fluff", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WonderBlocks.STEM_LOG.getDefaultState()), new SimpleBlockStateProvider(WonderBlocks.DANDELION_FLUFF.getDefaultState()), new DandelionFluffFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0)), new StraightTrunkPlacer(6, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build()));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(WorldOfWonder.MOD_ID, key), configuredFeature);
    }
}
