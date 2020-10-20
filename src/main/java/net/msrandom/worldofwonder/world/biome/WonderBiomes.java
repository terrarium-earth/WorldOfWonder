package net.msrandom.worldofwonder.world.biome;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;

import java.util.ArrayList;

public class WonderBiomes {
    public static final DeferredRegister<Biome> REGISTRY = DeferredRegister.create(ForgeRegistries.BIOMES, WorldOfWonder.MOD_ID);
    public static final Biome DANDELION_FIELDS = add("dandelion_fields", DandelionFieldsBiome.make());

    @SuppressWarnings("deprecation")
    public static void init(RegistryEvent.Register<Biome> event) {
        //I'm sure there is a better way to get the key, but I can't figure out how..
        WorldGenRegistries.BIOME.getOptionalKey(DANDELION_FIELDS).ifPresent(key -> {
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(key, 3));
            addSpawnBiome(key);
        });
    }

    private static void addSpawnBiome(RegistryKey<Biome> biomeKey) {
        if (OverworldBiomeProvider.biomes instanceof ImmutableList<?>) {
            OverworldBiomeProvider.biomes = new ArrayList<>(OverworldBiomeProvider.biomes);
        }
        OverworldBiomeProvider.biomes.add(biomeKey);
    }

    private static Biome add(String name, Biome biome) {
        REGISTRY.register(name, () -> biome);
        return biome;
    }
}
