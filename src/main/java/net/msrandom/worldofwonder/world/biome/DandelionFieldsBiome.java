package net.msrandom.worldofwonder.world.biome;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.msrandom.worldofwonder.entity.WonderEntities;
import net.msrandom.worldofwonder.world.gen.WonderFeatures;

public class DandelionFieldsBiome {
    public static Biome make() {
        MobSpawnInfo.Builder mobInfo = new MobSpawnInfo.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
        generationSettings.addStructureStart(StructureFeatures.VILLAGE_PLAINS).addStructureStart(StructureFeatures.PILLAGER_OUTPOST);
        generationSettings.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, WonderFeatures.DANDELION.decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.05F, 1))));
        generationSettings.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, WonderFeatures.DANDELION_FLUFF.decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.01F, 1))));
        generationSettings.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.DANDELION.defaultBlockState()), new SimpleBlockPlacer()).build()).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.5F, 3))));
        DefaultBiomeFeatures.addDefaultOverworldLandStructures(generationSettings);
        DefaultBiomeFeatures.addDefaultCarvers(generationSettings);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(generationSettings);
        DefaultBiomeFeatures.addDefaultMonsterRoom(generationSettings);
        DefaultBiomeFeatures.addDefaultGrass(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultSoftDisks(generationSettings);
        DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        DefaultBiomeFeatures.addDefaultExtraVegetation(generationSettings);
        DefaultBiomeFeatures.addDefaultSprings(generationSettings);
        DefaultBiomeFeatures.addSurfaceFreezing(generationSettings);
        mobInfo.setPlayerCanSpawn();
        mobInfo.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4));
        mobInfo.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 10, 4, 4));
        mobInfo.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 10, 4, 4));
        mobInfo.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4));
        mobInfo.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3));
        mobInfo.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(WonderEntities.DANDE_LION, 30, 2, 3));
        mobInfo.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 10, 8, 8));
        mobInfo.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SPIDER, 100, 4, 4));
        mobInfo.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE, 95, 4, 4));
        mobInfo.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        mobInfo.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 100, 4, 4));
        mobInfo.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.CREEPER, 100, 4, 4));
        mobInfo.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 100, 4, 4));
        mobInfo.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 10, 1, 4));
        mobInfo.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.WITCH, 5, 1, 1));
        return new Biome.Builder().generationSettings(generationSettings.build()).mobSpawnSettings(mobInfo.build()).precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.PLAINS).depth(0.1F).scale(0.4F).temperature(0.7F).downfall(0.8F).specialEffects(new BiomeAmbience.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(7907327).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).build();
    }
}
