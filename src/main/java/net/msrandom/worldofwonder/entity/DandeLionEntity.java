package net.msrandom.worldofwonder.entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.msrandom.worldofwonder.WonderSounds;
import net.msrandom.worldofwonder.block.WonderBlocks;

import javax.annotation.Nullable;
import java.util.UUID;

public class DandeLionEntity extends TameableEntity {
    private static final DataParameter<Boolean> SHEARED = EntityDataManager.createKey(DandeLionEntity.class, DataSerializers.BOOLEAN);
    private int shearedTicks;

    public DandeLionEntity(EntityType<? extends DandeLionEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHEARED, false);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (rand.nextBoolean()) shear();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && !isSheared() && stack.getItem() == Items.SHEARS) {
            shear();
            this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, getSoundVolume(), 1);
            entityDropItem(new ItemStack(WonderBlocks.DANDELION_FLUFF, rand.nextInt(2) + 1));
        }
        return super.processInteract(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isRemote && shearedTicks > 0 && --shearedTicks == 0) {
            setSheared(false);
        }
    }

    @Override
    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        entityDropItem(new ItemStack(isSheared() ? WonderBlocks.DANDELION_PETALS : WonderBlocks.DANDELION_FLUFF, rand.nextInt(2) + 1));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return WonderSounds.DANDE_LION_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return WonderSounds.DANDE_LION_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return WonderSounds.DANDE_LION_HURT;
    }

    public boolean isSheared() {
        return this.dataManager.get(SHEARED);
    }

    private void setSheared(boolean sheared) {
        this.dataManager.set(SHEARED, sheared);
    }

    public void shear() {
        if (!world.isRemote) {
            shearedTicks = 12000;
            setSheared(true);
        }
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        DandeLionEntity entity = WonderEntities.DANDE_LION.create(this.world);
        UUID uuid = this.getOwnerId();
        if (uuid != null && entity != null) {
            entity.setOwnerId(uuid);
            entity.setTamed(true);
        }
        return entity;
    }
}
