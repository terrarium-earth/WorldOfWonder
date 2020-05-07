package net.msrandom.worldofwonder.entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DandelionEntity extends TameableEntity {
    public DandelionEntity(EntityType<? extends DandelionEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }
}
