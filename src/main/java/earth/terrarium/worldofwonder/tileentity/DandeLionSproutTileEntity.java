package earth.terrarium.worldofwonder.tileentity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import earth.terrarium.worldofwonder.entity.DandeLionEntity;
import earth.terrarium.worldofwonder.entity.WonderEntities;

import java.util.Objects;

public class DandeLionSproutTileEntity extends BlockEntity implements TickableBlockEntity {
    private int maxAge = -1;
    private int age;

    public DandeLionSproutTileEntity() {
        super(WonderTileEntities.DANDE_LION_SPROUT.get());
    }

    @Override
    public void load(BlockState state, CompoundTag compound) {
        age = compound.getInt("Age");
        super.load(state, compound);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.putInt("Age", age);
        return super.save(compound);
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
