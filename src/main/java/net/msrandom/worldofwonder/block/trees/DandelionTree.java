package net.msrandom.worldofwonder.block.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.msrandom.worldofwonder.world.gen.WonderFeatures;

import javax.annotation.Nullable;
import java.util.Random;

public class DandelionTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random randomIn, boolean largeHive) {
        return WonderFeatures.DANDELION;
    }
}
