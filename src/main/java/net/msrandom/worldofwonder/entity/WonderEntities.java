package net.msrandom.worldofwonder.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;

public class WonderEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = new DeferredRegister<>(ForgeRegistries.ENTITIES, WorldOfWonder.MOD_ID);
    public static final EntityType<DandelionEntity> DANDELION = add("dandelion", DandelionEntity::new, EntityClassification.CREATURE, 0.6f, 0.5f);

    private static <T extends Entity> EntityType<T> add(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
        EntityType<T> type = EntityType.Builder.create(factory, classification).size(width, height).build(name);
        REGISTRY.register(name, () -> type);
        return type;
    }
}
