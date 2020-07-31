package net.msrandom.worldofwonder.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
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
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.msrandom.worldofwonder.WonderSounds;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.WonderBlocks;

import javax.annotation.Nullable;
import java.util.UUID;

public class DandeLionEntity extends TameableEntity {
    private static final DataParameter<Boolean> SHEARED = EntityDataManager.createKey(DandeLionEntity.class, DataSerializers.BOOLEAN);
    private boolean madeChild;
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
        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            Item item = stack.getItem();

            if (item instanceof SpawnEggItem && ((SpawnEggItem) item).hasType(stack.getTag(), this.getType())) {
                DandeLionEntity child = WonderEntities.DANDE_LION.create(world);
                if (child != null) {
                    UUID id = getOwnerId();
                    if (id != null) {
                        child.setOwnerId(id);
                        child.setTamed(true);
                    }
                    child.setGrowingAge(-24000);
                    child.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
                    this.world.addEntity(child);
                    if (stack.hasDisplayName()) {
                        child.setCustomName(stack.getDisplayName());
                    }

                    this.onChildSpawnFromEgg(player, child);
                    if (!player.abilities.isCreativeMode) {
                        stack.shrink(1);
                    }
                }

                return true;
            }

            if (!isSheared() && item == Items.SHEARS) {
                shear();
                this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, getSoundVolume(), 1);
                entityDropItem(new ItemStack(WonderBlocks.DANDELION_FLUFF, rand.nextInt(2) + 1));
                return true;
            }

            if (this.isTamed()) {
                if (item == Items.BONE_MEAL) {
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
            } else if (Block.getBlockFromItem(item).isIn(BlockTags.SMALL_FLOWERS) && !this.isAngry()) {
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
            if (this.isBreedingItem(stack)) {
                if (this.getGrowingAge() == 0 && this.canBreed()) {
                    if (flowersAround()) {
                        this.consumeItemFromStack(player, stack);
                        this.setInLove(player);
                        player.swing(hand, true);
                    } else {
                        player.sendStatusMessage(new TranslationTextComponent("entity." + WorldOfWonder.MOD_ID + ".dande_lion.no_flowers").setStyle(new Style().setColor(TextFormatting.RED)), true);
                    }
                    return true;
                }

                if (this.isChild()) {
                    this.consumeItemFromStack(player, stack);
                    this.ageUp((int) (this.getGrowingAge() / -20.0 * 0.1), true);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return Block.getBlockFromItem(stack.getItem()).isIn(BlockTags.TALL_FLOWERS);
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
        this.dataManager.set(TAMED, angry ? (byte) (b0 | 2) : (byte) (b0 & -3));
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
    public boolean attackEntityFrom(DamageSource source, float amount) {
        this.setSitting(false);
        return super.attackEntityFrom(source, amount);
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
        return isTamed() ? WonderSounds.DANDE_LION_PUR : WonderSounds.DANDE_LION_AMBIENT;
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
        if (ageable instanceof DandeLionEntity) {
            if (((DandeLionEntity) ageable).madeChild) ((DandeLionEntity) ageable).madeChild = false;
            else {
                madeChild = true;
                for (int i = 0; i < rand.nextInt(2) + 3; i++) {
                    DandeLionSeedEntity seed = WonderEntities.DANDE_LION_SEED.create(world);
                    if (seed != null) {
                        double rotation = Math.toRadians(rand.nextInt(360));
                        seed.setPosition(getPosX(), getPosY(), getPosZ());
                        seed.setTarget(getPosX() + Math.sin(rotation) * 16.0, getPosZ() + Math.cos(-rotation) * 16.0);
                        world.addEntity(seed);
                    }
                }

                ServerPlayerEntity serverplayerentity = getLoveCause();
                if (serverplayerentity == null) {
                    serverplayerentity = ((DandeLionEntity) ageable).getLoveCause();
                }

                if (serverplayerentity != null) {
                    serverplayerentity.addStat(Stats.ANIMALS_BRED);
                    CriteriaTriggers.BRED_ANIMALS.trigger(serverplayerentity, this, (AnimalEntity) ageable, null);
                }

                setGrowingAge(6000);
                ageable.setGrowingAge(6000);
                resetInLove();
                ((AnimalEntity) ageable).resetInLove();

                this.world.setEntityState(this, (byte)18);
                this.world.setEntityState(ageable, (byte)18);
                if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                    this.world.addEntity(new ExperienceOrbEntity(this.world, getPosX(), getPosY(), getPosZ(), rand.nextInt(7) + 1));
                }
            }
        }
        return null;
    }

    private boolean flowersAround() {
        BlockPos p = getPosition();
        int flowers = 0;
        for (int i = -16; i <= 16; i++) {
            for (int j = -16; j <= 16; j++) {
                BlockPos pos = p.add(i, 0, j);
                BlockState current = world.getBlockState(pos);
                BlockState down = world.getBlockState(pos.down());
                if (!down.isSolid()) current = down;
                if (current.isIn(BlockTags.FLOWERS) && ++flowers >= 6) {
                    return true;
                }
            }
        }
        return false;
    }
}
