package net.msrandom.worldofwonder.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class PottedSproutBlock extends FlowerPotBlock {
    private static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    public PottedSproutBlock() {
        super(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WonderBlocks.DANDE_LION_SPROUT, Block.Properties.of(Material.DECORATION).noOcclusion());
        registerDefaultState(defaultBlockState().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}
