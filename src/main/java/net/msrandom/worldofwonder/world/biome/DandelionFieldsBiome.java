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
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        generationSettings.withStructure(StructureFeatures.field_244154_t).withStructure(StructureFeatures.field_244135_a);
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, WonderFeatures.DANDELION.withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(1, 0.05F, 1))));
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, WonderFeatures.DANDELION_FLUFF.withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(1, 0.01F, 1))));
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.DANDELION.getDefaultState()), new SimpleBlockPlacer()).build()).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(1, 0.5F, 3))));
        DefaultBiomeFeatures.withStrongholdAndMineshaft(generationSettings);
        DefaultBiomeFeatures.withCavesAndCanyons(generationSettings);
        DefaultBiomeFeatures.withCommonOverworldBlocks(generationSettings);
        DefaultBiomeFeatures.withMonsterRoom(generationSettings);
        DefaultBiomeFeatures.withTallGrass(generationSettings);
        DefaultBiomeFeatures.withOverworldOres(generationSettings);
        DefaultBiomeFeatures.withDisks(generationSettings);
        DefaultBiomeFeatures.withDefaultFlowers(generationSettings);
        DefaultBiomeFeatures.withNormalMushroomGeneration(generationSettings);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(generationSettings);
        DefaultBiomeFeatures.withLavaAndWaterSprings(generationSettings);
        DefaultBiomeFeatures.withFrozenTopLayer(generationSettings);
        mobInfo.isValidSpawnBiomeForPlayer();
        mobInfo.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4));
        mobInfo.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 10, 4, 4));
        mobInfo.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 10, 4, 4));
        mobInfo.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4));
        mobInfo.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3));
        mobInfo.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(WonderEntities.DANDE_LION, 50, 2, 3));
        mobInfo.withSpawner(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 10, 8, 8));
        mobInfo.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SPIDER, 100, 4, 4));
        mobInfo.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE, 95, 4, 4));
        mobInfo.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        mobInfo.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 100, 4, 4));
        mobInfo.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.CREEPER, 100, 4, 4));
        mobInfo.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 100, 4, 4));
        mobInfo.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 10, 1, 4));
        mobInfo.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.WITCH, 5, 1, 1));
        return new Biome.Builder().withGenerationSettings(generationSettings.build()).withMobSpawnSettings(mobInfo.copy()).precipitation(Biome.RainType.RAIN).category(Biome.Category.PLAINS).depth(0.1F).scale(0.4F).temperature(0.7F).downfall(0.8F).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(7907327).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).build();
    }
}
