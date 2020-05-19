package net.msrandom.worldofwonder.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
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
    protected void registerGoals() {
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, entity -> !(entity instanceof DandeLionEntity)));

    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6);
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(0.2);
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
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Angry", this.isAngry());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setAngry(compound.getBoolean("Angry"));
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (stack.getItem() instanceof SpawnEggItem) {
                return super.processInteract(player, hand);
            } else {

                if (!isSheared() && stack.getItem() == Items.SHEARS) {
                    shear();
                    this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, getSoundVolume(), 1);
                    entityDropItem(new ItemStack(WonderBlocks.DANDELION_FLUFF, rand.nextInt(2) + 1));
                    return true;
                }

                if (this.isTamed()) {
                    if (stack.getItem() == Items.BONE_MEAL) {
                        if (!player.abilities.isCreativeMode) {
                            stack.shrink(1);
                        }

                        this.heal(4);
                        if (shearedTicks > 0) {
                            shearedTicks -= 3000;
                            if (shearedTicks < 0) {
                                shearedTicks = 0;
                                setSheared(false);
                            }
                        }
                        return true;
                    }

                    if (this.isOwner(player) && !this.isBreedingItem(stack)) {
                        this.sitGoal.setSitting(!this.isSitting());
                        this.isJumping = false;
                        this.navigator.clearPath();
                        this.setAttackTarget(null);
                    }
                } else if (BlockTags.SMALL_FLOWERS.contains(Block.getBlockFromItem(stack.getItem())) && !this.isAngry()) {
                    if (!player.abilities.isCreativeMode) {
                        stack.shrink(1);
                    }

                    if (this.rand.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                        this.setTamedBy(player);
                        this.navigator.clearPath();
                        this.setAttackTarget(null);
                        this.sitGoal.setSitting(true);
                        this.world.setEntityState(this, (byte) 7);
                    } else {
                        this.world.setEntityState(this, (byte) 6);
                    }

                    return true;
                }
            }
        }
        return super.processInteract(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BlockTags.TALL_FLOWERS.contains(Block.getBlockFromItem(stack.getItem()));
    }

    @Override
    public boolean canMateWith(AnimalEntity otherAnimal) {
        if (otherAnimal != this && this.isTamed() && otherAnimal instanceof DandeLionEntity) {
            DandeLionEntity dandeLion = (DandeLionEntity) otherAnimal;
            return dandeLion.isTamed() && !dandeLion.isSitting() && this.isInLove() && dandeLion.isInLove();
        }
        return false;
    }

    public boolean isAngry() {
        return (this.dataManager.get(TAMED) & 2) != 0;
    }

    public void setAngry(boolean angry) {
        byte b0 = this.dataManager.get(TAMED);
        if (angry) {
            this.dataManager.set(TAMED, (byte) (b0 | 2));
        } else {
            this.dataManager.set(TAMED, (byte) (b0 & -3));
        }
    }

    @Override
    public void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);
        if (entitylivingbaseIn == null) {
            this.setAngry(false);
        } else if (!this.isTamed()) {
            this.setAngry(true);
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            if (this.getAttackTarget() == null && this.isAngry()) {
                this.setAngry(false);
            }
            if (shearedTicks > 0 && --shearedTicks == 0) {
                setSheared(false);
            }
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

    public boolean canBeLeashedTo(PlayerEntity player) {
        return !this.isAngry() && super.canBeLeashedTo(player);
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
