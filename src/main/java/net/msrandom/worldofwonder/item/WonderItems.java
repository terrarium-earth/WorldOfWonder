package net.msrandom.worldofwonder.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;

public class WonderItems {
    public static final DeferredRegister<Item> REGISTRY = new DeferredRegister<>(ForgeRegistries.ITEMS, WorldOfWonder.MOD_ID);
    public static final Item STEM_BOAT = add("stem_boat", new StemBoatItem());
    public static final Item DANDELION_HAT = add("dandelion_hat", new ItemDandelionHat());

    private static <T extends Item> T add(String name, T item) {
        REGISTRY.register(name, () -> item);
        return item;
    }
}
