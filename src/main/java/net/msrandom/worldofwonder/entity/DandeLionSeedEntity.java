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
import net.minecraftforge.fml.network.NetworkHooks;
import net.msrandom.worldofwonder.block.WonderBlocks;

public class DandeLionSeedEntity extends Entity {
    private static final DataParameter<Float> X = EntityDataManager.createKey(DandeLionSeedEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> Z = EntityDataManager.createKey(DandeLionSeedEntity.class, DataSerializers.FLOAT);

    public DandeLionSeedEntity(EntityType<? extends DandeLionSeedEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(X, 0f);
        this.dataManager.register(Z, 0f);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.dataManager.set(X, compound.getFloat("X"));
        this.dataManager.set(Z, compound.getFloat("Z"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putFloat("X", this.dataManager.get(X));
        compound.putFloat("Z", this.dataManager.get(Z));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setTarget(double x, double z) {
        this.dataManager.set(X, (float) x);
        this.dataManager.set(Z, (float) z);
    }

    @Override
    public void tick() {
        super.tick();

        if (isAlive()) {
            //Calculate the motion
            setMotion(MathHelper.clamp(this.dataManager.get(X) - getMotion().getX(), -0.2, 0.2), Math.sin(ticksExisted * 0.1) / 5.0, MathHelper.clamp(this.dataManager.get(Z) - getMotion().getZ(), -0.2, 0.2));

            //Move using the motion
            setPosition(getPosX() + getMotion().getX(), getPosY() + getMotion().getY(), getPosZ() + getMotion().getZ());

            if (ticksExisted > 20 && world.getBlockState(new BlockPos(getPosX(), getPosY() - 1, getPosZ())).isSolid()) {
                world.setBlockState(getPosition(), WonderBlocks.DANDE_LION_SPROUT.getDefaultState());
                remove();
            }
        }
    }
}
