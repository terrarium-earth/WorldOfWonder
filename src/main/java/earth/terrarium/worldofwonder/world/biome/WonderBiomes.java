package earth.terrarium.worldofwonder.world.biome;

import earth.terrarium.worldofwonder.WorldOfWonder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WonderBiomes {
    public static final DeferredRegister<Biome> REGISTRY = DeferredRegister.create(ForgeRegistries.BIOMES, WorldOfWonder.MOD_ID);
    public static final RegistryObject<Biome> DANDELION_FIELDS = REGISTRY.register("dandelion_fields", DandelionFieldsBiome::make);
}
