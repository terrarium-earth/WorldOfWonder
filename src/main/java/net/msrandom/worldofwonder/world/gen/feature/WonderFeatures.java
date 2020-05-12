package net.msrandom.worldofwonder.world.gen.feature;

import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;

public class WonderFeatures {
    public static final DeferredRegister<Feature<?>> REGISTRY = new DeferredRegister<>(ForgeRegistries.FEATURES, WorldOfWonder.MOD_ID);
    public static final AbstractTreeFeature<BaseTreeFeatureConfig> DANDELION_TREE = add("dandelion_tree", new DandelionTreeFeature());
    public static final AbstractTreeFeature<BaseTreeFeatureConfig> FLUFF_TREE = add("fluff_tree", new DandelionFluffTreeFeature());

    private static <T extends Feature<?>> T add(String name, T feature) {
        REGISTRY.register(name, () -> feature);
        return feature;
    }
}
