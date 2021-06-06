package net.msrandom.worldofwonder.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DandelionFluffBlock extends DandelionBlock {
    public DandelionFluffBlock() {
        super(Block.Properties.of(Material.WOOL).strength(0.2f).sound(SoundType.WOOL));
    }

    @Override
    public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        super.fallOn(worldIn, pos, entityIn, fallDistance * 0.5f);
    }
}
