package net.msrandom.worldofwonder.compat;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.entity.WonderEntities;
import net.msrandom.worldofwonder.item.ItemEvents;
import net.msrandom.worldofwonder.item.WonderItems;
import net.msrandom.worldofwonder.world.biome.WonderBiomes;

import java.util.ArrayList;

public class WonderVanillaCompat {
    @SuppressWarnings("deprecation")
    public static void init(FMLCommonSetupEvent event) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation(WorldOfWonder.MOD_ID, "dande_lion_sprout"), WonderBlocks.POTTED_DANDE_LION_SPROUT);
        DispenserBlock.registerBehavior(WonderItems.BLOOM_MEAL.get(), ItemEvents.BLOOM_MEAL_DISPENSE);
        EntitySpawnPlacementRegistry.register(WonderEntities.DANDE_LION.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, AnimalEntity::checkAnimalSpawnRules);
        registerCompostable(WonderBlocks.DANDELION_PETALS.get().asItem(), 0.3F);
        registerCompostable(WonderBlocks.DANDELION_FLUFF.get().asItem(), 0.3F);

        //I'm sure there is a better way to get the key, but I can't figure out how..
        WorldGenRegistries.BIOME.getResourceKey(WonderBiomes.DANDELION_FIELDS.get()).ifPresent(key -> {
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(key, 3));
            addSpawnBiome(key);
        });
    }

    private static void addSpawnBiome(RegistryKey<Biome> biomeKey) {
        if (OverworldBiomeProvider.POSSIBLE_BIOMES instanceof ImmutableList<?>) {
            OverworldBiomeProvider.POSSIBLE_BIOMES = new ArrayList<>(OverworldBiomeProvider.POSSIBLE_BIOMES);
        }
        OverworldBiomeProvider.POSSIBLE_BIOMES.add(biomeKey);
    }

    public static void registerCompostable(IItemProvider itemIn, float chance) {
        ComposterBlock.COMPOSTABLES.put(itemIn, chance);
    }
}
