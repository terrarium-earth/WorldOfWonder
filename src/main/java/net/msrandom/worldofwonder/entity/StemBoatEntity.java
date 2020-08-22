
package net.msrandom.worldofwonder.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class StemBoatEntity extends BoatEntity {
    public StemBoatEntity(EntityType<? extends BoatEntity> p_i50129_1_, World p_i50129_2_) {
        super(p_i50129_1_, p_i50129_2_);
    }

    public StemBoatEntity(World worldIn, double x, double y, double z) {
        this(WonderEntities.STEM_BOAT, worldIn);
        this.setPosition(x, y, z);
        this.setMotion(Vec3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
