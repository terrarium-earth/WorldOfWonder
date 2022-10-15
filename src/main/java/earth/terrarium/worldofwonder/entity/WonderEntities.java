package earth.terrarium.worldofwonder.entity;

import earth.terrarium.worldofwonder.WorldOfWonder;
import earth.terrarium.worldofwonder.item.WonderItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WonderEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, WorldOfWonder.MOD_ID);
    public static final RegistryObject<EntityType<DandeLionEntity>> DANDE_LION = add("dande_lion", DandeLionEntity::new, MobCategory.CREATURE, 0.8f, 1.1f, 0x79ae45, 0xf2db26);
    public static final RegistryObject<EntityType<StemBoatEntity>> STEM_BOAT = add("stem_boat", StemBoatEntity::new, MobCategory.MISC, 1.375f, 0.5625f);
    public static final RegistryObject<EntityType<DandeLionSeedEntity>> DANDE_LION_SEED = add("dande_lion_seed", DandeLionSeedEntity::new, MobCategory.MISC, 1f, 1f);

    public static void setupAttributes(EntityAttributeCreationEvent event) {
        event.put(DANDE_LION.get(), DandeLionEntity.registerAttributes().build());
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> add(String name, EntityType.EntityFactory<T> factory, MobCategory classification, float width, float height, int eggPrimary, int eggSecondary) {
        EntityType<T> type = create(name, factory, classification, width, height);
        WonderItems.REGISTRY.register(name + "_spawn_egg", () -> new SpawnEggItem(type, eggPrimary, eggSecondary, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        //Hacks around deferred registering to allow for the spawn egg to be made the same way vanilla does it
        return REGISTRY.register(name, () -> type);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> add(String name, EntityType.EntityFactory<T> factory, MobCategory classification, float width, float height) {
        return REGISTRY.register(name, () -> create(name, factory, classification, width, height));
    }

    private static <T extends Entity> EntityType<T> create(String name, EntityType.EntityFactory<T> factory, MobCategory classification, float width, float height) {
        return EntityType.Builder.of(factory, classification).sized(width, height).build(name);
    }
}
