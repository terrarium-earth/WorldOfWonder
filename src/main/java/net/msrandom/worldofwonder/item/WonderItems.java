package net.msrandom.worldofwonder.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;

public class WonderItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WorldOfWonder.MOD_ID);
    //public static final Item STEM_BOAT = add("stem_boat", new StemBoatItem());
    public static final Item DANDELION_HAT = add("dandelion_hat", new ItemDandelionHat());
    public static final Item BLOOM_MEAL = add("bloom_meal", new Item(new Item.Properties().group(ItemGroup.MATERIALS)));

    private static <T extends Item> T add(String name, T item) {
        REGISTRY.register(name, () -> item);
        return item;
    }
}
