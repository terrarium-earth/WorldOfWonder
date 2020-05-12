package net.msrandom.worldofwonder.world.gen.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.msrandom.worldofwonder.block.WonderBlocks;

import java.util.Random;
import java.util.Set;

public class DandelionTreeFeature extends AbstractTreeFeature<BaseTreeFeatureConfig> {
    public static final BaseTreeFeatureConfig CONFIG = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WonderBlocks.STEM_WOOD.getDefaultState()), new SimpleBlockStateProvider(WonderBlocks.DANDELION_PETALS.getDefaultState())).func_225568_b_();

    public DandelionTreeFeature() {
        super(dynamic -> CONFIG);
    }

    @Override
    protected boolean func_225557_a_(IWorldGenerationReader p_225557_1_, Random p_225557_2_, BlockPos p_225557_3_, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox p_225557_6_, BaseTreeFeatureConfig p_225557_7_) {
        return false;
    }
}
