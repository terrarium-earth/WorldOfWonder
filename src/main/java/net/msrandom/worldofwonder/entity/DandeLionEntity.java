package net.msrandom.worldofwonder.entity;

import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.msrandom.worldofwonder.WonderSounds;
import net.msrandom.worldofwonder.block.WonderBlocks;

public class DandeLionEntity extends TameableEntity {
    private static final DataParameter<Boolean> SHEARED = EntityDataManager.createKey(DandeLionEntity.class, DataSerializers.BOOLEAN);
    private int shearedTicks;
    public static final Predicate<LivingEntity> TARGET_ENTITIES = (entitytype) -> {
        //EntityType<?> entitytype = p_213440_0_.getType();
        return entitytype instanceof AgeableEntity && !(entitytype instanceof DandeLionEntity) && !(entitytype instanceof VillagerEntity);
     };

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
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, AgeableEntity.class, false, TARGET_ENTITIES));
        
     }
    
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.25F);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(0.2D);
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
        } else if (this.world.isRemote) {
        	return this.isOwner(player) || stack.getTag().equals(BlockTags.SMALL_FLOWERS);
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
