package net.msrandom.worldofwonder.compat;

import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.entity.WonderEntities;
import net.msrandom.worldofwonder.item.ItemEvents;
import net.msrandom.worldofwonder.item.WonderItems;

public class WonderVanillaCompat {
    public static void init(FMLCommonSetupEvent event) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation(WorldOfWonder.MOD_ID, "dande_lion_sprout"), () -> WonderBlocks.POTTED_DANDE_LION_SPROUT);
        DispenserBlock.registerDispenseBehavior(WonderItems.BLOOM_MEAL, ItemEvents.BLOOM_MEAL_DISPENSE);
        EntitySpawnPlacementRegistry.register(WonderEntities.DANDE_LION, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, AnimalEntity::canAnimalSpawn);
        registerCompostable(WonderBlocks.DANDELION_PETALS.asItem(), 0.3F);
        registerCompostable(WonderBlocks.DANDELION_FLUFF.asItem(), 0.3F);
    }

    public static void registerCompostable(IItemProvider itemIn, float chance) {
        ComposterBlock.CHANCES.put(itemIn, chance);
    }
}
