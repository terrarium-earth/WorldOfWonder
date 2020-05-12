package net.msrandom.worldofwonder.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.client.CSteerBoatPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.item.WonderItems;

import javax.annotation.Nullable;
import java.util.List;

public class StemBoatEntity extends Entity {
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(StemBoatEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.createKey(StemBoatEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(StemBoatEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> LEFT_PADDLE = EntityDataManager.createKey(StemBoatEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> RIGHT_PADDLE = EntityDataManager.createKey(StemBoatEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ROCKING_TICKS = EntityDataManager.createKey(StemBoatEntity.class, DataSerializers.VARINT);
    private final float[] paddlePositions = new float[2];
    private float outOfControlTicks;
    private float deltaRotation;
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;
    private boolean leftInputDown;
    private boolean rightInputDown;
    private boolean forwardInputDown;
    private boolean backInputDown;
    private double waterLevel;
    private float boatGlide;
    private BoatEntity.Status status;
    private BoatEntity.Status previousStatus;
    private double lastYd;
    private boolean rocking;
    private boolean falling;
    private float rockingIntensity;
    private float rockingAngle;
    private float prevRockingAngle;

    public StemBoatEntity(EntityType<? extends StemBoatEntity> type, World world) {
        super(type, world);
        this.preventEntitySpawning = true;
    }

    public StemBoatEntity(World worldIn, double x, double y, double z) {
        this(WonderEntities.STEM_BOAT, worldIn);
        this.setPosition(x, y, z);
        this.setMotion(Vec3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    protected boolean func_225502_at_() {
        return false;
    }

    protected void registerData() {
        this.dataManager.register(TIME_SINCE_HIT, 0);
        this.dataManager.register(FORWARD_DIRECTION, 1);
        this.dataManager.register(DAMAGE_TAKEN, 0.0F);
        this.dataManager.register(LEFT_PADDLE, false);
        this.dataManager.register(RIGHT_PADDLE, false);
        this.dataManager.register(ROCKING_TICKS, 0);
    }

    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return entityIn.canBePushed() ? entityIn.getBoundingBox() : null;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getBoundingBox();
    }

    public boolean canBePushed() {
        return true;
    }

    public double getMountedYOffset() {
        return -0.1D;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.world.isRemote && isAlive()) {
            if (source instanceof IndirectEntityDamageSource && source.getTrueSource() != null && this.isPassenger(source.getTrueSource())) {
                return false;
            } else {
                this.setForwardDirection(-this.getForwardDirection());
                this.setTimeSinceHit(10);
                this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
                this.markVelocityChanged();
                boolean flag = source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity)source.getTrueSource()).abilities.isCreativeMode;
                if (flag || this.getDamageTaken() > 40.0F) {
                    if (!flag && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                        this.entityDropItem(WonderItems.STEM_BOAT);
                    }

                    this.remove();
                }

                return true;
            }
        } else {
            return true;
        }
    }

    public void onEnterBubbleColumnWithAirAbove(boolean downwards) {
        if (!this.world.isRemote) {
            this.rocking = true;
            this.falling = downwards;
            if (this.getRockingTicks() == 0) {
                this.setRockingTicks(60);
            }
        }

        this.world.addParticle(ParticleTypes.SPLASH, this.func_226277_ct_() + this.rand.nextDouble(), this.func_226278_cu_() + 0.7D, this.func_226281_cx_() + this.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
        if (this.rand.nextInt(20) == 0) {
            this.world.playSound(this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_(), this.getSplashSound(), this.getSoundCategory(), 1.0F, 0.8F + 0.4F * this.rand.nextFloat(), false);
        }

    }

    public void applyEntityCollision(Entity entityIn) {
        if (entityIn instanceof BoatEntity) {
            if (entityIn.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.applyEntityCollision(entityIn);
            }
        } else if (entityIn.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.applyEntityCollision(entityIn);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }

    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @OnlyIn(Dist.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYaw = yaw;
        this.lerpPitch = pitch;
        this.lerpSteps = 10;
    }

    public Direction getAdjustedHorizontalFacing() {
        return this.getHorizontalFacing().rotateY();
    }

    public void tick() {
        this.previousStatus = this.status;
        this.status = this.getBoatStatus();
        if (this.status != BoatEntity.Status.UNDER_WATER && this.status != BoatEntity.Status.UNDER_FLOWING_WATER) {
            this.outOfControlTicks = 0.0F;
        } else {
            ++this.outOfControlTicks;
        }

        if (!this.world.isRemote && this.outOfControlTicks >= 60.0F) {
            this.removePassengers();
        }

        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if (this.getDamageTaken() > 0.0F) {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }

        super.tick();
        this.tickLerp();
        if (this.canPassengerSteer()) {
            if (this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof PlayerEntity)) {
                this.setPaddleState(false, false);
            }

            this.updateMotion();
            if (this.world.isRemote) {
                this.controlBoat();
                this.world.sendPacketToServer(new CSteerBoatPacket(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.move(MoverType.SELF, this.getMotion());
        } else {
            this.setMotion(Vec3d.ZERO);
        }

        this.updateRocking();

        for(int i = 0; i <= 1; ++i) {
            if (this.getPaddleState(i)) {
                if (!this.isSilent() && (double)(this.paddlePositions[i] % ((float)Math.PI * 2F)) <= (double)((float)Math.PI / 4F) && ((double)this.paddlePositions[i] + (double)((float)Math.PI / 8F)) % (double)((float)Math.PI * 2F) >= (double)((float)Math.PI / 4F)) {
                    SoundEvent soundevent = this.getPaddleSound();
                    if (soundevent != null) {
                        Vec3d vec3d = this.getLook(1.0F);
                        double d0 = i == 1 ? -vec3d.z : vec3d.z;
                        double d1 = i == 1 ? vec3d.x : -vec3d.x;
                        this.world.playSound(null, this.func_226277_ct_() + d0, this.func_226278_cu_(), this.func_226281_cx_() + d1, soundevent, this.getSoundCategory(), 1.0F, 0.8F + 0.4F * this.rand.nextFloat());
                    }
                }

                this.paddlePositions[i] = (float)((double)this.paddlePositions[i] + (double)((float)Math.PI / 8F));
            } else {
                this.paddlePositions[i] = 0.0F;
            }
        }

        this.doBlockCollisions();
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow(0.2, -0.01, 0.2), EntityPredicates.pushableBy(this));
        if (!list.isEmpty()) {
            boolean flag = !this.world.isRemote && !(this.getControllingPassenger() instanceof PlayerEntity);

            for (Entity entity : list) {
                if (!entity.isPassenger(this)) {
                    if (flag && this.getPassengers().size() < 2 && !entity.isPassenger() && entity.getWidth() < this.getWidth() && entity instanceof LivingEntity && !(entity instanceof WaterMobEntity) && !(entity instanceof PlayerEntity)) {
                        entity.startRiding(this);
                    } else {
                        this.applyEntityCollision(entity);
                    }
                }
            }
        }
    }

    private void updateRocking() {
        if (this.world.isRemote) {
            int i = this.getRockingTicks();
            if (i > 0) {
                this.rockingIntensity += 0.05F;
            } else {
                this.rockingIntensity -= 0.1F;
            }

            this.rockingIntensity = MathHelper.clamp(this.rockingIntensity, 0.0F, 1.0F);
            this.prevRockingAngle = this.rockingAngle;
            this.rockingAngle = 10.0F * MathHelper.sin((0.5F * (float)this.world.getGameTime())) * this.rockingIntensity;
        } else {
            if (!this.rocking) {
                this.setRockingTicks(0);
            }

            int k = this.getRockingTicks();
            if (k > 0) {
                --k;
                this.setRockingTicks(k);
                int j = 60 - k - 1;
                if (j > 0 && k == 0) {
                    this.setRockingTicks(0);
                    Vec3d vec3d = this.getMotion();
                    if (this.falling) {
                        this.setMotion(vec3d.add(0.0D, -0.7D, 0.0D));
                        this.removePassengers();
                    } else {
                        this.setMotion(vec3d.x, this.isPassenger(PlayerEntity.class) ? 2.7D : 0.6D, vec3d.z);
                    }
                }

                this.rocking = false;
            }
        }

    }

    @Nullable
    protected SoundEvent getPaddleSound() {
        switch(this.getBoatStatus()) {
            case IN_WATER:
            case UNDER_WATER:
            case UNDER_FLOWING_WATER:
                return SoundEvents.ENTITY_BOAT_PADDLE_WATER;
            case ON_LAND:
                return SoundEvents.ENTITY_BOAT_PADDLE_LAND;
            case IN_AIR:
            default:
                return null;
        }
    }

    private void tickLerp() {
        if (this.canPassengerSteer()) {
            this.lerpSteps = 0;
            this.func_213312_b(this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.func_226277_ct_() + (this.lerpX - this.func_226277_ct_()) / (double)this.lerpSteps;
            double d1 = this.func_226278_cu_() + (this.lerpY - this.func_226278_cu_()) / (double)this.lerpSteps;
            double d2 = this.func_226281_cx_() + (this.lerpZ - this.func_226281_cx_()) / (double)this.lerpSteps;
            double d3 = MathHelper.wrapDegrees(this.lerpYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + d3 / (double)this.lerpSteps);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.lerpPitch - (double)this.rotationPitch) / (double)this.lerpSteps);
            --this.lerpSteps;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }

    public void setPaddleState(boolean left, boolean right) {
        this.dataManager.set(LEFT_PADDLE, left);
        this.dataManager.set(RIGHT_PADDLE, right);
    }

    @OnlyIn(Dist.CLIENT)
    public float getRowingTime(int side, float limbSwing) {
        return this.getPaddleState(side) ? (float)MathHelper.clampedLerp((double)this.paddlePositions[side] - (double)((float)Math.PI / 8F), (double)this.paddlePositions[side], (double)limbSwing) : 0.0F;
    }

    private BoatEntity.Status getBoatStatus() {
        BoatEntity.Status boatentity$status = this.getUnderwaterStatus();
        if (boatentity$status != null) {
            this.waterLevel = this.getBoundingBox().maxY;
            return boatentity$status;
        } else if (this.checkInWater()) {
            return BoatEntity.Status.IN_WATER;
        } else {
            float f = this.getBoatGlide();
            if (f > 0.0F) {
                this.boatGlide = f;
                return BoatEntity.Status.ON_LAND;
            } else {
                return BoatEntity.Status.IN_AIR;
            }
        }
    }

    public float getWaterLevelAbove() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.ceil(axisalignedbb.maxX);
        int k = MathHelper.floor(axisalignedbb.maxY);
        int l = MathHelper.ceil(axisalignedbb.maxY - this.lastYd);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.ceil(axisalignedbb.maxZ);

        try (BlockPos.PooledMutable pos = BlockPos.PooledMutable.retain()) {
            label:
            for(int k1 = k; k1 < l; ++k1) {
                float f = 0.0F;

                for(int l1 = i; l1 < j; ++l1) {
                    for(int i2 = i1; i2 < j1; ++i2) {
                        pos.setPos(l1, k1, i2);
                        IFluidState ifluidstate = this.world.getFluidState(pos);
                        if (ifluidstate.isTagged(FluidTags.WATER)) {
                            f = Math.max(f, ifluidstate.func_215679_a(this.world, pos));
                        }

                        if (f >= 1.0F) {
                            continue label;
                        }
                    }
                }

                if (f < 1.0F) {
                    return pos.getY() + f;
                }
            }

            return l + 1;
        }
    }

    public float getBoatGlide() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY - 0.001D, axisalignedbb.minZ, axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        int i = MathHelper.floor(axisalignedbb1.minX) - 1;
        int j = MathHelper.ceil(axisalignedbb1.maxX) + 1;
        int k = MathHelper.floor(axisalignedbb1.minY) - 1;
        int l = MathHelper.ceil(axisalignedbb1.maxY) + 1;
        int i1 = MathHelper.floor(axisalignedbb1.minZ) - 1;
        int j1 = MathHelper.ceil(axisalignedbb1.maxZ) + 1;
        VoxelShape voxelshape = VoxelShapes.create(axisalignedbb1);
        float f = 0.0F;
        int k1 = 0;

        try (BlockPos.PooledMutable blockpos$pooledmutable = BlockPos.PooledMutable.retain()) {
            for(int l1 = i; l1 < j; ++l1) {
                for(int i2 = i1; i2 < j1; ++i2) {
                    int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
                    if (j2 != 2) {
                        for(int k2 = k; k2 < l; ++k2) {
                            if (j2 <= 0 || k2 != k && k2 != l - 1) {
                                blockpos$pooledmutable.setPos(l1, k2, i2);
                                BlockState blockstate = this.world.getBlockState(blockpos$pooledmutable);
                                if (!(blockstate.getBlock() instanceof LilyPadBlock) && VoxelShapes.compare(blockstate.getCollisionShape(this.world, blockpos$pooledmutable).withOffset(l1, k2, i2), voxelshape, IBooleanFunction.AND)) {
                                    f += blockstate.getSlipperiness(this.world, blockpos$pooledmutable, this);
                                    ++k1;
                                }
                            }
                        }
                    }
                }
            }
        }

        return f / (float)k1;
    }

    private boolean checkInWater() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.ceil(axisalignedbb.maxX);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.ceil(axisalignedbb.minY + 0.001D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.ceil(axisalignedbb.maxZ);
        boolean flag = false;
        this.waterLevel = Double.MIN_VALUE;

        try (BlockPos.PooledMutable blockpos$pooledmutable = BlockPos.PooledMutable.retain()) {
            for(int k1 = i; k1 < j; ++k1) {
                for(int l1 = k; l1 < l; ++l1) {
                    for(int i2 = i1; i2 < j1; ++i2) {
                        blockpos$pooledmutable.setPos(k1, l1, i2);
                        IFluidState ifluidstate = this.world.getFluidState(blockpos$pooledmutable);
                        if (ifluidstate.isTagged(FluidTags.WATER)) {
                            float f = (float)l1 + ifluidstate.func_215679_a(this.world, blockpos$pooledmutable);
                            this.waterLevel = Math.max(f, this.waterLevel);
                            flag |= axisalignedbb.minY < (double)f;
                        }
                    }
                }
            }
        }

        return flag;
    }

    @Nullable
    private BoatEntity.Status getUnderwaterStatus() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        double d0 = axisalignedbb.maxY + 0.001D;
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.ceil(axisalignedbb.maxX);
        int k = MathHelper.floor(axisalignedbb.maxY);
        int l = MathHelper.ceil(d0);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.ceil(axisalignedbb.maxZ);
        boolean flag = false;

        try (BlockPos.PooledMutable blockpos$pooledmutable = BlockPos.PooledMutable.retain()) {
            for(int k1 = i; k1 < j; ++k1) {
                for(int l1 = k; l1 < l; ++l1) {
                    for(int i2 = i1; i2 < j1; ++i2) {
                        blockpos$pooledmutable.setPos(k1, l1, i2);
                        IFluidState ifluidstate = this.world.getFluidState(blockpos$pooledmutable);
                        if (ifluidstate.isTagged(FluidTags.WATER) && d0 < (double)((float)blockpos$pooledmutable.getY() + ifluidstate.func_215679_a(this.world, blockpos$pooledmutable))) {
                            if (!ifluidstate.isSource()) {
                                return BoatEntity.Status.UNDER_FLOWING_WATER;
                            }

                            flag = true;
                        }
                    }
                }
            }
        }

        return flag ? BoatEntity.Status.UNDER_WATER : null;
    }

    private void updateMotion() {
        double d0 = (double)-0.04F;
        double d1 = this.hasNoGravity() ? 0.0D : (double)-0.04F;
        double d2 = 0.0D;
        float momentum = 0.05F;
        if (this.previousStatus == BoatEntity.Status.IN_AIR && this.status != BoatEntity.Status.IN_AIR && this.status != BoatEntity.Status.ON_LAND) {
            this.waterLevel = this.func_226283_e_(1.0D);
            this.setPosition(this.func_226277_ct_(), (double)(this.getWaterLevelAbove() - this.getHeight()) + 0.101D, this.func_226281_cx_());
            this.setMotion(this.getMotion().mul(1.0D, 0.0D, 1.0D));
            this.lastYd = 0.0D;
            this.status = BoatEntity.Status.IN_WATER;
        } else {
            if (this.status == BoatEntity.Status.IN_WATER) {
                d2 = (this.waterLevel - this.func_226278_cu_()) / (double)this.getHeight();
                momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.UNDER_FLOWING_WATER) {
                d1 = -7.0E-4D;
                momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.UNDER_WATER) {
                d2 = 0.01;
                momentum = 0.45F;
            } else if (this.status == BoatEntity.Status.IN_AIR) {
                momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.ON_LAND) {
                momentum = this.boatGlide;
                if (this.getControllingPassenger() instanceof PlayerEntity) {
                    this.boatGlide /= 2.0F;
                }
            }

            Vec3d vec3d = this.getMotion();
            this.setMotion(vec3d.x * (double) momentum, vec3d.y + d1, vec3d.z * (double) momentum);
            this.deltaRotation *= momentum;
            if (d2 > 0.0D) {
                Vec3d vec3d1 = this.getMotion();
                this.setMotion(vec3d1.x, (vec3d1.y + d2 * 0.06153846016296973D) * 0.75D, vec3d1.z);
            }
        }

    }

    private void controlBoat() {
        if (this.isBeingRidden()) {
            double f = 0.0F;
            if (this.leftInputDown) {
                --this.deltaRotation;
            }

            if (this.rightInputDown) {
                ++this.deltaRotation;
            }

            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
                f += 0.005;
            }

            this.rotationYaw += this.deltaRotation;
            if (this.forwardInputDown) {
                f += 0.04;
            }

            if (this.backInputDown) {
                f -= 0.005;
            }

            this.setMotion(this.getMotion().add(Math.sin(Math.toRadians(-this.rotationYaw)) * f, 0.0D, Math.cos(Math.toRadians(this.rotationYaw)) * f));
            this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);
        }
    }

    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float f = 0.0F;
            float f1 = (float)((!isAlive() ? (double)0.01F : this.getMountedYOffset()) + passenger.getYOffset());
            if (this.getPassengers().size() > 1) {
                int i = this.getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = 0.2F;
                } else {
                    f = -0.6F;
                }

                if (passenger instanceof AnimalEntity) {
                    f = (float)((double)f + 0.2D);
                }
            }

            Vec3d vec3d = (new Vec3d(f, 0.0D, 0.0D)).rotateYaw((float) (Math.toRadians(-this.rotationYaw) - (Math.PI / 2F)));
            passenger.setPosition(this.func_226277_ct_() + vec3d.x, this.func_226278_cu_() + (double)f1, this.func_226281_cx_() + vec3d.z);
            passenger.rotationYaw += this.deltaRotation;
            passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
            this.applyYawToEntity(passenger);
            if (passenger instanceof AnimalEntity && this.getPassengers().size() > 1) {
                int j = passenger.getEntityId() % 2 == 0 ? 90 : 270;
                passenger.setRenderYawOffset(((AnimalEntity)passenger).renderYawOffset + (float)j);
                passenger.setRotationYawHead(passenger.getRotationYawHead() + (float)j);
            }

        }
    }

    protected void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setRenderYawOffset(this.rotationYaw);
        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
        entityToUpdate.prevRotationYaw += f1 - f;
        entityToUpdate.rotationYaw += f1 - f;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    @OnlyIn(Dist.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate) {
        this.applyYawToEntity(entityToUpdate);
    }

    protected void writeAdditional(CompoundNBT compound) {
    }

    protected void readAdditional(CompoundNBT compound) {
    }

    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        if (player.func_226563_dT_()) {
            return false;
        } else {
            return (!this.world.isRemote && this.outOfControlTicks < 60.0F) && player.startRiding(this);
        }
    }

    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        this.lastYd = this.getMotion().y;
        if (!this.isPassenger()) {
            if (onGroundIn) {
                if (this.fallDistance > 3.0F) {
                    if (this.status != BoatEntity.Status.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.func_225503_b_(this.fallDistance, 1.0F);
                    if (!this.world.isRemote && isAlive()) {
                        this.remove();
                        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            for(int i = 0; i < 3; ++i) {
                                this.entityDropItem(WonderBlocks.STEM_PLANKS);
                            }

                            for(int j = 0; j < 2; ++j) {
                                this.entityDropItem(Items.STICK);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            } else if (!this.world.getFluidState((new BlockPos(this)).down()).isTagged(FluidTags.WATER) && y < 0.0D) {
                this.fallDistance = (float)((double)this.fallDistance - y);
            }

        }
    }

    public boolean getPaddleState(int side) {
        return this.dataManager.<Boolean>get(side == 0 ? LEFT_PADDLE : RIGHT_PADDLE) && this.getControllingPassenger() != null;
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float damageTaken) {
        this.dataManager.set(DAMAGE_TAKEN, damageTaken);
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken() {
        return this.dataManager.get(DAMAGE_TAKEN);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int timeSinceHit) {
        this.dataManager.set(TIME_SINCE_HIT, timeSinceHit);
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return this.dataManager.get(TIME_SINCE_HIT);
    }

    private void setRockingTicks(int p_203055_1_) {
        this.dataManager.set(ROCKING_TICKS, p_203055_1_);
    }

    private int getRockingTicks() {
        return this.dataManager.get(ROCKING_TICKS);
    }

    @OnlyIn(Dist.CLIENT)
    public float getRockingAngle(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.prevRockingAngle, this.rockingAngle);
    }

    public void setForwardDirection(int forwardDirection) {
        this.dataManager.set(FORWARD_DIRECTION, forwardDirection);
    }

    public int getForwardDirection() {
        return this.dataManager.get(FORWARD_DIRECTION);
    }

    protected boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() < 2 && !this.areEyesInFluid(FluidTags.WATER);
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @OnlyIn(Dist.CLIENT)
    public void updateInputs(boolean p_184442_1_, boolean p_184442_2_, boolean p_184442_3_, boolean p_184442_4_) {
        this.leftInputDown = p_184442_1_;
        this.rightInputDown = p_184442_2_;
        this.forwardInputDown = p_184442_3_;
        this.backInputDown = p_184442_4_;
    }

    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (this.canPassengerSteer() && this.lerpSteps > 0) {
            this.lerpSteps = 0;
            this.setPositionAndRotation(this.lerpX, this.lerpY, this.lerpZ, (float)this.lerpYaw, (float)this.lerpPitch);
        }
    }
}
