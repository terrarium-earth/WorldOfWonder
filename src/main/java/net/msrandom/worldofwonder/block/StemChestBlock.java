package net.msrandom.worldofwonder.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IBlockReader;
import net.msrandom.worldofwonder.compat.WonderQuarkCompat;

import java.util.function.Supplier;

public class StemChestBlock extends ChestBlock {
    public StemChestBlock() {
        this(WonderQuarkCompat.STEM_CHEST_ENTITY::get);
    }

    public StemChestBlock(Supplier<TileEntityType<? extends ChestTileEntity>> tileEntityTypeIn) {
        super(Block.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD), tileEntityTypeIn);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return blockEntityType.get().create();
    }
}
