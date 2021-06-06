package net.msrandom.worldofwonder.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.msrandom.worldofwonder.entity.DandeLionEntity;
import net.msrandom.worldofwonder.entity.WonderEntities;

import java.util.Objects;

public class DandeLionSproutTileEntity extends TileEntity implements ITickableTileEntity {
    private int maxAge = -1;
    private int age;

    public DandeLionSproutTileEntity() {
        super(WonderTileEntities.DANDE_LION_SPROUT.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        age = compound.getInt("Age");
        super.load(state, compound);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
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
