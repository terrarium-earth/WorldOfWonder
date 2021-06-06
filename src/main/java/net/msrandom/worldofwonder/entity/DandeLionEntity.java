package net.msrandom.worldofwonder.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;
import net.msrandom.worldofwonder.WonderSounds;
import net.msrandom.worldofwonder.block.WonderBlocks;

import javax.annotation.Nullable;
import java.util.UUID;

public class DandeLionEntity extends TameableEntity {
    private static final DataParameter<Boolean> SHEARED = EntityDataManager.defineId(DandeLionEntity.class, DataSerializers.BOOLEAN);
    private boolean madeChild;
    private int shearedTicks;

    public DandeLionEntity(EntityType<? extends DandeLionEntity> type, World levelIn) {
        super(type, levelIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, entity -> !(entity instanceof DandeLionEntity)));

    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.ATTACK_KNOCKBACK, 0.2);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHEARED, false);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld levelIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (random.nextBoolean()) shear();
        return super.finalizeSpawn(levelIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Angry", this.isAngry());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setAngry(compound.getBoolean("Angry"));
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (!level.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);
            Item item = stack.getItem();

            if (item instanceof SpawnEggItem && ((SpawnEggItem) item).spawnsEntity(stack.getTag(), this.getType())) {
                DandeLionEntity child = WonderEntities.DANDE_LION.create(level);
                if (child != null) {
                    UUID id = getOwnerUUID();
                    if (id != null) {
                        child.setOwnerUUID(id);
                        child.setTame(true);
                    }
                    child.setAge(-24000);
                    child.setPos(this.getX(), this.getY(), this.getZ());
                    this.level.addFreshEntity(child);
                    if (stack.hasCustomHoverName()) {
                        child.setCustomName(stack.getDisplayName());
                    }

                    this.onOffspringSpawnedFromEgg(player, child);
                    if (!player.abilities.instabuild) {
                        stack.shrink(1);
                    }
                }

                return ActionResultType.SUCCESS;
            }

            if (!isSheared() && item == Items.SHEARS) {
                shear();
                this.playSound(SoundEvents.SHEEP_SHEAR, getSoundVolume(), 1);
                spawnAtLocation(new ItemStack(WonderBlocks.DANDELION_FLUFF, random.nextInt(2) + 1));
                return ActionResultType.SUCCESS;
            }

            if (this.isTame()) {
                if (item == Items.BONE_MEAL) {
                    if (!player.abilities.instabuild) {
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
                    return ActionResultType.SUCCESS;
                }

                if (this.isOwnedBy(player) && !this.isFood(stack)) {
                    this.setOrderedToSit(!this.isSleeping());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                }
            } else if (Block.byItem(item).is(BlockTags.SMALL_FLOWERS) && !this.isAngry()) {
                if (!player.abilities.instabuild) {
                    stack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 6);
                }

                return ActionResultType.SUCCESS;
            }
            if (this.isFood(stack)) {
                if (this.getAge() == 0 && this.canBreed()) {
                    if (flowersAround()) {
                        this.usePlayerItem(player, stack);
                        this.setInLove(player);
                        player.swing(hand, true);
                    } else {
                        level.broadcastEntityEvent(this, (byte) 5);
                    }
                    return ActionResultType.SUCCESS;
                }

                if (this.isBaby()) {
                    this.usePlayerItem(player, stack);
                    this.ageUp((int) (this.getAge() / -20.0 * 0.1), true);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 5) {
            for(int i = 0; i <= 6; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.ANGRY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
            }
        } else{
            super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return Block.byItem(stack.getItem()).is(BlockTags.TALL_FLOWERS);
    }

    @Override
    public boolean canMate(AnimalEntity otherAnimal) {
        if (otherAnimal != this && this.isTame() && otherAnimal instanceof DandeLionEntity) {
            DandeLionEntity dandeLion = (DandeLionEntity) otherAnimal;
            return dandeLion.isTame() && !dandeLion.isInSittingPose() && this.isInLove() && dandeLion.isInLove();
        }
        return false;
    }

    public boolean isAngry() {
        return (this.entityData.get(DATA_FLAGS_ID) & 2) != 0;
    }

    public void setAngry(boolean angry) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        this.entityData.set(DATA_FLAGS_ID, angry ? (byte) (b0 | 2) : (byte) (b0 & -3));
    }

    @Override
    public void setTarget(@Nullable LivingEntity entitylivingbaseIn) {
        super.setTarget(entitylivingbaseIn);
        if (entitylivingbaseIn == null) {
            this.setAngry(false);
        } else if (!this.isTame()) {
            this.setAngry(true);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        this.setOrderedToSit(false);
        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            if (this.getTarget() == null && this.isAngry()) {
                this.setAngry(false);
            }
            if (shearedTicks > 0 && --shearedTicks == 0) {
                setSheared(false);
            }
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        spawnAtLocation(new ItemStack(isSheared() ? WonderBlocks.DANDELION_PETALS : WonderBlocks.DANDELION_FLUFF, random.nextInt(2) + 1));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isTame() ? WonderSounds.DANDE_LION_PUR : WonderSounds.DANDE_LION_AMBIENT;
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
        return this.entityData.get(SHEARED);
    }

    private void setSheared(boolean sheared) {
        this.entityData.set(SHEARED, sheared);
    }

    public void shear() {
        if (!level.isClientSide) {
            shearedTicks = 12000;
            setSheared(true);
        }
    }

    public boolean canBeLeashed(PlayerEntity player) {
        return !this.isAngry() && super.canBeLeashed(player);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity ageable) {
        if (ageable instanceof DandeLionEntity) {
            if (((DandeLionEntity) ageable).madeChild) ((DandeLionEntity) ageable).madeChild = false;
            else {
                madeChild = true;
                for (int i = 0; i < random.nextInt(2) + 3; i++) {
                    DandeLionSeedEntity seed = WonderEntities.DANDE_LION_SEED.create(level);
                    if (seed != null) {
                        double rotation = Math.toRadians(random.nextInt(360));
                        seed.setPos(getX(), getY(), getZ());
                        seed.setTarget(getX() + Math.sin(rotation) * 16.0, getZ() + Math.cos(-rotation) * 16.0);
                        level.addFreshEntity(seed);
                    }
                }

                ServerPlayerEntity serverplayerentity = getLoveCause();
                if (serverplayerentity == null) {
                    serverplayerentity = ((DandeLionEntity) ageable).getLoveCause();
                }

                if (serverplayerentity != null) {
                    serverplayerentity.awardStat(Stats.ANIMALS_BRED);
                    CriteriaTriggers.BRED_ANIMALS.trigger(serverplayerentity, this, (AnimalEntity) ageable, null);
                }

                setAge(6000);
                ageable.setAge(6000);
                resetLove();
                ((AnimalEntity) ageable).resetLove();

                this.level.broadcastEntityEvent(this, (byte)18);
                this.level.broadcastEntityEvent(ageable, (byte)18);
                if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                    this.level.addFreshEntity(new ExperienceOrbEntity(this.level, getX(), getY(), getZ(), random.nextInt(7) + 1));
                }
            }
        }
        return null;
    }

    private boolean flowersAround() {
        BlockPos p = getOnPos();
        int flowers = 0;
        for (int i = -16; i <= 16; i++) {
            for (int j = -16; j <= 16; j++) {
                BlockPos pos = p.offset(i, 0, j);
                BlockState current = level.getBlockState(pos);
                BlockState down = level.getBlockState(pos.below());
                if (!down.canOcclude()) current = down;
                if (current.is(BlockTags.FLOWERS) && ++flowers >= 6) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (target instanceof CreeperEntity || target instanceof GhastEntity) {
            return false;
        } else {
            if (target instanceof DandeLionEntity) {
                DandeLionEntity dandeLion = (DandeLionEntity)target;
                return !dandeLion.isTame() || dandeLion.getOwner() != owner;
            } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).canHarmPlayer((PlayerEntity)target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity)target).isTamed()) {
                return false;
            } else {
                return !(target instanceof TameableEntity) || !((TameableEntity)target).isTame();
            }
        }
    }
}
