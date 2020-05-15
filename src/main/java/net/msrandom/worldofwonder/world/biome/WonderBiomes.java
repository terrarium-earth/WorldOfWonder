package net.msrandom.worldofwonder.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;

public class WonderBiomes {
    public static final DeferredRegister<Biome> REGISTRY = new DeferredRegister<>(ForgeRegistries.BIOMES, WorldOfWonder.MOD_ID);
    public static final Biome DANDELION_FIELDS = add("dandelion_fields", new DandelionFieldsBiome());

    public static void registerTypes(RegistryEvent.Register<?> event) {
        if (event.getRegistry().getRegistrySuperType() == Biome.class) {
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(WonderBiomes.DANDELION_FIELDS, 50));
            BiomeManager.addSpawnBiome(WonderBiomes.DANDELION_FIELDS);
            BiomeDictionary.addTypes(WonderBiomes.DANDELION_FIELDS, BiomeDictionary.Type.PLAINS);
        }
    }

    private static <T extends Biome> T add(String name, T biome) {
        REGISTRY.register(name, () -> biome);
        return biome;
    }
}
