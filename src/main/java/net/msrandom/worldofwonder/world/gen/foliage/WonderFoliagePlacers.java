package net.msrandom.worldofwonder.world.gen.foliage;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;

public class WonderFoliagePlacers {
    public static final DeferredRegister<FoliagePlacerType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, WorldOfWonder.MOD_ID);
    public static final RegistryObject<FoliagePlacerType<DandelionFoliagePlacer>> DANDELION = REGISTRY.register("dandelion", () -> new FoliagePlacerType<>(DandelionFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<DandelionFluffFoliagePlacer>> DANDELION_FLUFF = REGISTRY.register("dandelion_fluff", () -> new FoliagePlacerType<>(DandelionFluffFoliagePlacer.CODEC));

    private static <P extends FoliagePlacer> FoliagePlacerType<P> register(String name, Codec<P> codec) {
        FoliagePlacerType<P> type = new FoliagePlacerType<>(codec);
        REGISTRY.register(name, () -> type);
        return type;
    }
}
