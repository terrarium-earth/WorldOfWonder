package earth.terrarium.worldofwonder.world.gen.foliage;

import earth.terrarium.worldofwonder.WorldOfWonder;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WonderFoliagePlacers {
    public static final DeferredRegister<FoliagePlacerType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, WorldOfWonder.MOD_ID);
    public static final RegistryObject<FoliagePlacerType<DandelionFoliagePlacer>> DANDELION = REGISTRY.register("dandelion", () -> new FoliagePlacerType<>(DandelionFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<DandelionFluffFoliagePlacer>> DANDELION_FLUFF = REGISTRY.register("dandelion_fluff", () -> new FoliagePlacerType<>(DandelionFluffFoliagePlacer.CODEC));
}
