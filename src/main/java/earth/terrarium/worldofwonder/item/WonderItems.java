package earth.terrarium.worldofwonder.item;

import earth.terrarium.worldofwonder.WorldOfWonder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WonderItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WorldOfWonder.MOD_ID);
    public static final RegistryObject<Item> STEM_BOAT = REGISTRY.register("stem_boat", StemBoatItem::new);
    public static final RegistryObject<Item> DANDELION_HAT = REGISTRY.register("dandelion_hat", ItemDandelionHat::new);
    public static final RegistryObject<Item> BLOOM_MEAL = REGISTRY.register("bloom_meal", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
}
