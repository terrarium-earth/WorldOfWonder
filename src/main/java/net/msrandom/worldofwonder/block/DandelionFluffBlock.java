package net.msrandom.worldofwonder.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DandelionFluffBlock extends DandelionBlock {
    public DandelionFluffBlock() {
        super(Block.Properties.create(Material.WOOL).hardnessAndResistance(0.2f).sound(SoundType.CLOTH));
    }

    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance * 0.5f);
    }
}
