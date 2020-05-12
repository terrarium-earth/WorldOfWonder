package net.msrandom.worldofwonder.entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && !isSheared() && stack.getItem() == Items.SHEARS) {
            setSheared(true);
            shearedTicks = 12000;
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

    public boolean isSheared() {
        return this.dataManager.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.dataManager.set(SHEARED, sheared);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        DandeLionEntity entity = WonderEntities.DANDELION.create(this.world);
        UUID uuid = this.getOwnerId();
        if (uuid != null && entity != null) {
            entity.setOwnerId(uuid);
            entity.setTamed(true);
        }
        return entity;
    }
}
