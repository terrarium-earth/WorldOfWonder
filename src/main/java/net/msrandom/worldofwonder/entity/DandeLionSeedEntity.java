package net.msrandom.worldofwonder.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;
import net.msrandom.worldofwonder.block.WonderBlocks;

public class DandeLionSeedEntity extends Entity {
    private static final DataParameter<Float> X = EntityDataManager.defineId(DandeLionSeedEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> Z = EntityDataManager.defineId(DandeLionSeedEntity.class, DataSerializers.FLOAT);

    public DandeLionSeedEntity(EntityType<? extends DandeLionSeedEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(X, 0f);
        this.entityData.define(Z, 0f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound) {
        this.entityData.set(X, compound.getFloat("X"));
        this.entityData.set(Z, compound.getFloat("Z"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        compound.putFloat("X", this.entityData.get(X));
        compound.putFloat("Z", this.entityData.get(Z));
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setTarget(double x, double z) {
        this.entityData.set(X, (float) x);
        this.entityData.set(Z, (float) z);
    }

    @Override
    public void tick() {
        super.tick();

        if (isAlive()) {
            //Calculate the motion
            setDeltaMovement(MathHelper.clamp(this.entityData.get(X) - getDeltaMovement().x(), -0.2, 0.2), Math.sin(tickCount * 0.1) / 5.0, MathHelper.clamp(this.entityData.get(Z) - getDeltaMovement().z(), -0.2, 0.2));

            //Move using the motion
            setPos(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());

            if (tickCount > 20 && level.getBlockState(new BlockPos(getX(), getY() - 1, getZ())).canOcclude()) {
                level.setBlock(blockPosition(), WonderBlocks.DANDE_LION_SPROUT.get().defaultBlockState(), Constants.BlockFlags.NOTIFY_NEIGHBORS | Constants.BlockFlags.BLOCK_UPDATE);
                remove();
            }
        }
    }
}
