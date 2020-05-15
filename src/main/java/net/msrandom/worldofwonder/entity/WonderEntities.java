package net.msrandom.worldofwonder.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.item.WonderItems;

public class WonderEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = new DeferredRegister<>(ForgeRegistries.ENTITIES, WorldOfWonder.MOD_ID);
    public static final EntityType<DandeLionEntity> DANDE_LION = add("dande_lion", DandeLionEntity::new, EntityClassification.CREATURE, 0.8f, 1f, 0x0, 0x0);
    public static final EntityType<StemBoatEntity> STEM_BOAT = add("stem_boat", StemBoatEntity::new, EntityClassification.MISC, 1.375f, 0.5625f);

    private static <T extends Entity> EntityType<T> add(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height, int eggPrimary, int eggSecondary) {
        EntityType<T> type = add(name, factory, classification, width, height);
        WonderItems.REGISTRY.register(name + "_spawn_egg", () -> new SpawnEggItem(type, eggPrimary, eggSecondary, new Item.Properties().group(ItemGroup.MISC)));
        return type;
    }

    private static <T extends Entity> EntityType<T> add(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
        EntityType<T> type = EntityType.Builder.create(factory, classification).size(width, height).build(name);
        REGISTRY.register(name, () -> type);
        return type;
    }
}
