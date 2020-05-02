package net.msrandom.worldofwonder.item;

import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;

public class WonderItems {
    public static final DeferredRegister<Item> REGISTRY = new DeferredRegister<>(ForgeRegistries.ITEMS, WorldOfWonder.MOD_ID);
    //public static final Item STEM_BOAT = add("stem_boat", new WonderBoatItem(new Item.Properties().maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    private static <T extends Item> T add(String name, T item) {
        REGISTRY.register(name, () -> item);
        return item;
    }
}
