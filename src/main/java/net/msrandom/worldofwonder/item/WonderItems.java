package net.msrandom.worldofwonder.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;

public class WonderItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WorldOfWonder.MOD_ID);
    public static final RegistryObject<Item> STEM_BOAT = REGISTRY.register("stem_boat", StemBoatItem::new);
    public static final RegistryObject<Item> DANDELION_HAT = REGISTRY.register("dandelion_hat", ItemDandelionHat::new);
    public static final RegistryObject<Item> BLOOM_MEAL = REGISTRY.register("bloom_meal", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
}
