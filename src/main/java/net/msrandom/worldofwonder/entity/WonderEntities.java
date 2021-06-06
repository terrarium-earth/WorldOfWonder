package net.msrandom.worldofwonder.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.item.WonderItems;

public class WonderEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, WorldOfWonder.MOD_ID);
    public static final RegistryObject<EntityType<DandeLionEntity>> DANDE_LION = add("dande_lion", DandeLionEntity::new, EntityClassification.CREATURE, 0.8f, 1.1f, 0x79ae45, 0xf2db26);
    public static final RegistryObject<EntityType<StemBoatEntity>> STEM_BOAT = add("stem_boat", StemBoatEntity::new, EntityClassification.MISC, 1.375f, 0.5625f);
    public static final RegistryObject<EntityType<DandeLionSeedEntity>> DANDE_LION_SEED = add("dande_lion_seed", DandeLionSeedEntity::new, EntityClassification.MISC, 1f, 1f);

    public static void setupAttributes(EntityAttributeCreationEvent event) {
        event.put(DANDE_LION.get(), DandeLionEntity.registerAttributes().build());
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> add(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height, int eggPrimary, int eggSecondary) {
        EntityType<T> type = create(name, factory, classification, width, height);
        WonderItems.REGISTRY.register(name + "_spawn_egg", () -> new SpawnEggItem(type, eggPrimary, eggSecondary, new Item.Properties().tab(ItemGroup.TAB_MISC)));
        //Hacks around deferred registering to allow for the spawn egg to be made the same way vanilla does it
        return REGISTRY.register(name, () -> type);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> add(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
        return REGISTRY.register(name, () -> create(name, factory, classification, width, height));
    }

    private static <T extends Entity> EntityType<T> create(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
        return EntityType.Builder.of(factory, classification).sized(width, height).build(name);
    }
}
