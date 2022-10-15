package earth.terrarium.worldofwonder.block;

import earth.terrarium.worldofwonder.compat.WonderQuarkCompat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.BlockGetter;

import java.util.function.Supplier;

public class StemChestBlock extends ChestBlock {
    public StemChestBlock() {
        this(WonderQuarkCompat.STEM_CHEST_ENTITY::get);
    }

    public StemChestBlock(Supplier<BlockEntityType<? extends ChestBlockEntity>> tileEntityTypeIn) {
        super(Block.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD), tileEntityTypeIn);
    }

    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        return blockEntityType.get().create();
    }
}
