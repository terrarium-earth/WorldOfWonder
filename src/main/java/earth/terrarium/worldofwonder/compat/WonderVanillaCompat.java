package earth.terrarium.worldofwonder.compat;

import com.google.common.collect.ImmutableList;
import earth.terrarium.worldofwonder.WorldOfWonder;
import earth.terrarium.worldofwonder.item.ItemEvents;
import earth.terrarium.worldofwonder.item.WonderItems;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import earth.terrarium.worldofwonder.block.WonderBlocks;
import earth.terrarium.worldofwonder.entity.WonderEntities;
import earth.terrarium.worldofwonder.world.biome.WonderBiomes;

import java.util.ArrayList;

public class WonderVanillaCompat {
    public static void init(FMLCommonSetupEvent event) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation(WorldOfWonder.MOD_ID, "dande_lion_sprout"), WonderBlocks.POTTED_DANDE_LION_SPROUT);
        DispenserBlock.registerBehavior(WonderItems.BLOOM_MEAL.get(), ItemEvents.BLOOM_MEAL_DISPENSE);
        SpawnPlacements.register(WonderEntities.DANDE_LION.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Animal::checkAnimalSpawnRules);
        registerCompostable(WonderBlocks.DANDELION_PETALS.get().asItem(), 0.3F);
        registerCompostable(WonderBlocks.DANDELION_FLUFF.get().asItem(), 0.3F);

        //Thanks forge
        ForgeRegistry<Biome> biomeRegistry = (ForgeRegistry<Biome>) ForgeRegistries.BIOMES;
        ResourceKey<Biome> key = biomeRegistry.getKey(biomeRegistry.getID(WonderBiomes.DANDELION_FIELDS.get()));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(key, 3));

        WoodType.register(WorldOfWonder.STEM_WOOD_TYPE);

        addSpawnBiome(key);
    }

    private static void addSpawnBiome(ResourceKey<Biome> biomeKey) {
        if (OverworldBiomeSource.POSSIBLE_BIOMES instanceof ImmutableList<?>) {
            OverworldBiomeSource.POSSIBLE_BIOMES = new ArrayList<>(OverworldBiomeSource.POSSIBLE_BIOMES);
        }
        OverworldBiomeSource.POSSIBLE_BIOMES.add(biomeKey);
    }

    public static void registerCompostable(ItemLike itemIn, float chance) {
        ComposterBlock.COMPOSTABLES.put(itemIn, chance);
    }
}
