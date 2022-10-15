package earth.terrarium.worldofwonder.tileentity;

import earth.terrarium.worldofwonder.entity.DandeLionEntity;
import earth.terrarium.worldofwonder.entity.WonderEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class DandeLionSproutTileEntity extends BlockEntity implements TickableBlockEntity {
    private int maxAge = -1;
    private int age;

    public DandeLionSproutTileEntity(BlockPos pos, BlockState state) {
        super(WonderTileEntities.DANDE_LION_SPROUT.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        age = tag.getInt("Age");
        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("Age", age);
        super.saveAdditional(tag);
    }

    @Override
    public void tick() {
        if (hasLevel() && !Objects.requireNonNull(level).isClientSide && ++age >= (maxAge == -1 ? maxAge = 48000 + level.random.nextInt(12000) : maxAge)) {
            DandeLionEntity entity = WonderEntities.DANDE_LION.get().create(this.level);
            if (entity != null) {
                entity.setAge(-24000);
                entity.setPos(getBlockPos().getX() + 0.5, getBlockPos().getY(), getBlockPos().getZ() + 0.5);
                level.addFreshEntity(entity);
                level.destroyBlock(getBlockPos(), false);
            }
        }
    }
}
