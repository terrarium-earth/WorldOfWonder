package net.msrandom.worldofwonder.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;

public class WonderBiomes {
    public static final DeferredRegister<Biome> REGISTRY = new DeferredRegister<>(ForgeRegistries.BIOMES, WorldOfWonder.MOD_ID);
    public static final Biome DANDELION_FIELDS = add("dandelion_fields", new DandelionFieldsBiome());

    private static <T extends Biome> T add(String name, T biome) {
        REGISTRY.register(name, () -> biome);
        return biome;
    }
}
