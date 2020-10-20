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
        super(WonderTileEntities.DANDE_LION_SPROUT);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        age = compound.getInt("Age");
        super.read(state, compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("Age", age);
        return super.write(compound);
    }

    @Override
    public void tick() {
        if (hasWorld() && !Objects.requireNonNull(world).isRemote && ++age >= (maxAge == -1 ? maxAge = 48000 + world.rand.nextInt(12000) : maxAge)) {
            DandeLionEntity entity = WonderEntities.DANDE_LION.create(this.world);
            if (entity != null) {
                entity.setGrowingAge(-24000);
                entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0F, 0.0F);
                world.addEntity(entity);
                world.destroyBlock(pos, false);
            }
        }
    }
}
