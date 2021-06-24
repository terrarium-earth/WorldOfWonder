
package net.msrandom.worldofwonder.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.item.WonderItems;

public class StemBoatEntity extends BoatEntity {
    public StemBoatEntity(EntityType<? extends BoatEntity> p_i50129_1_, World p_i50129_2_) {
        super(p_i50129_1_, p_i50129_2_);
    }

    public StemBoatEntity(World worldIn, double x, double y, double z) {
        this(WonderEntities.STEM_BOAT.get(), worldIn);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vector3d.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public Item getDropItem() {
        return WonderItems.STEM_BOAT.get();
    }

    @Override
    protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
        this.lastYd = this.getDeltaMovement().y;
        if (!this.isPassenger()) {
            if (p_184231_3_) {
                if (this.fallDistance > 3.0F) {
                    if (this.status != BoatEntity.Status.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F);
                    if (!this.level.isClientSide && isAlive()) {
                        this.remove();
                        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            for(int i = 0; i < 3; ++i) {
                                this.spawnAtLocation(WonderBlocks.STEM_PLANKS.get());
                            }

                            for(int j = 0; j < 2; ++j) {
                                this.spawnAtLocation(Items.STICK);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            } else if (!this.level.getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && p_184231_1_ < 0.0D) {
                this.fallDistance = (float)((double)this.fallDistance - p_184231_1_);
            }

        }
    }

    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
