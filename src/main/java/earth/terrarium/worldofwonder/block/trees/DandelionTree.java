package earth.terrarium.worldofwonder.block.trees;

import earth.terrarium.worldofwonder.world.gen.WonderFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class DandelionTree extends AbstractTreeGrower {
    @Nullable
    @Override
    protected Holder<ConfiguredFeature<TreeConfiguration, ?>> getConfiguredFeature(RandomSource random, boolean largeHive) {
        return WonderFeatures.DANDELION;
    }
}
