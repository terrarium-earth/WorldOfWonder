package earth.terrarium.worldofwonder.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class DandelionFluffBlock extends DandelionBlock {
    public DandelionFluffBlock() {
        super(Block.Properties.of(Material.WOOL).strength(0.2f).sound(SoundType.WOOL));
    }


    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.fallOn(level, state, pos, entity, fallDistance * 0.5f);
    }
}
