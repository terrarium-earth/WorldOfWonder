package net.msrandom.worldofwonder.block.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.msrandom.worldofwonder.world.gen.WonderFeatures;

import javax.annotation.Nullable;
import java.util.Random;

public class DandelionFluffTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random p_225546_1_, boolean p_225546_2_) {
        return WonderFeatures.DANDELION_FLUFF;
    }
}
