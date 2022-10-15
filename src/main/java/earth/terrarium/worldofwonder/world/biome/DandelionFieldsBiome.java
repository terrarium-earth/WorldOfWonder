package earth.terrarium.worldofwonder.world.biome;

import earth.terrarium.worldofwonder.entity.WonderEntities;
import earth.terrarium.worldofwonder.world.gen.WonderFeatures;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class DandelionFieldsBiome {
    public static Biome make() {
        MobSpawnSettings.Builder mobInfo = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GRASS);
        generationSettings.addStructureStart(Structures.VILLAGE_PLAINS).addStructureStart(Structures.PILLAGER_OUTPOST);

        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WonderFeatures.DANDELION
                .decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE)
                .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.05F, 1)))
        );
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WonderFeatures.DANDELION_FLUFF
                .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
                .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.01F, 1)))
        );

        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Feature.FLOWER
                .configured(new RandomPatchConfiguration.GrassConfigurationBuilder(
                        new SimpleStateProvider(Blocks.DANDELION.defaultBlockState()),
                        new SimpleBlockPlacer()
                ).build())
                .decorated(Features.Decorators.ADD_32)
                .decorated(Features.Decorators.HEIGHTMAP).squared()
                .decorated(FeatureDecorator.COUNT_NOISE.configured(new NoiseDependantDecoratorConfiguration(-0.8D, 15, 4)))
        );

        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH
                .configured(new RandomPatchConfiguration.GrassConfigurationBuilder(
                        new SimpleStateProvider(Blocks.GRASS.defaultBlockState()),
                        new SimpleBlockPlacer()
                ).build())
                .decorated(Features.Decorators.ADD_32)
                .decorated(Features.Decorators.HEIGHTMAP).squared()
                .decorated(FeatureDecorator.COUNT_NOISE.configured(new NoiseDependantDecoratorConfiguration(-0.8D, 15, 4)))
        );

        BiomeDefaultFeatures.addDefaultOverworldLandStructures(generationSettings);
        BiomeDefaultFeatures.addDefaultCarvers(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addPlainGrass(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);
        BiomeDefaultFeatures.addDefaultFlowers(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generationSettings);
        BiomeDefaultFeatures.addDefaultSprings(generationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
        mobInfo.setPlayerCanSpawn();
        mobInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
        mobInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4));
        mobInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
        mobInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));
        mobInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
        mobInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(WonderEntities.DANDE_LION.get(), 30, 2, 3));
        mobInfo.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
        mobInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 4, 4));
        mobInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 95, 4, 4));
        mobInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        mobInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 100, 4, 4));
        mobInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 4, 4));
        mobInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 100, 4, 4));
        mobInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4));
        mobInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1));
        return new Biome.BiomeBuilder().generationSettings(generationSettings.build()).mobSpawnSettings(mobInfo.build()).precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.PLAINS).depth(0.1F).scale(0.4F).temperature(0.7F).downfall(0.8F).specialEffects(new BiomeSpecialEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(7907327).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).build();
    }
}
