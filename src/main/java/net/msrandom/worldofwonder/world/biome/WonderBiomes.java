package net.msrandom.worldofwonder.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;

public class WonderBiomes {
    public static final DeferredRegister<Biome> REGISTRY = DeferredRegister.create(ForgeRegistries.BIOMES, WorldOfWonder.MOD_ID);
    public static final RegistryObject<Biome> DANDELION_FIELDS = REGISTRY.register("dandelion_fields", DandelionFieldsBiome::make);
}
