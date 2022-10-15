package earth.terrarium.worldofwonder.block.trees;

import earth.terrarium.worldofwonder.world.gen.WonderFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class DandelionFluffTree extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random p_225546_1_, boolean p_225546_2_) {
        return WonderFeatures.DANDELION_FLUFF;
    }
}
