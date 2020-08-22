package net.msrandom.worldofwonder.compat;

import net.minecraft.block.*;
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
    public static void init(@SuppressWarnings("unused") FMLCommonSetupEvent event) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation(WorldOfWonder.MOD_ID, "dande_lion_sprout"), () -> WonderBlocks.POTTED_DANDE_LION_SPROUT);
        DispenserBlock.registerDispenseBehavior(WonderItems.BLOOM_MEAL, ItemEvents.BLOOM_MEAL_DISPENSE);
        EntitySpawnPlacementRegistry.register(WonderEntities.DANDE_LION, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, AnimalEntity::canAnimalSpawn);

        registerCompostable(WonderBlocks.DANDELION_PETALS.asItem(), 0.3F);
        registerCompostable(WonderBlocks.DANDELION_FLUFF.asItem(), 0.3F);

        registerFlammable(WonderBlocks.STEM_LOG, 5, 5);
        registerFlammable(WonderBlocks.STRIPPED_STEM_LOG, 5, 5);
        registerFlammable(WonderBlocks.STEM_WOOD, 5, 5);
        registerFlammable(WonderBlocks.STRIPPED_STEM_WOOD, 5, 5);

        registerFlammable(WonderBlocks.STEM_PLANKS, 5, 20);
        registerFlammable(WonderBlocks.STEM_STAIRS, 5, 20);
        registerFlammable(WonderBlocks.STEM_SLAB, 5, 20);
        registerFlammable(WonderBlocks.STEM_FENCE, 5, 20);
        registerFlammable(WonderBlocks.STEM_FENCE_GATE, 5, 20);

        registerFlammable(WonderBlocks.DANDELION_FLUFF, 30, 60);
        registerFlammable(WonderBlocks.DANDELION_PETALS, 30, 60);
    }

    public static void registerCompostable(IItemProvider itemIn, float chance) {
        ComposterBlock.CHANCES.put(itemIn, chance);
    }

    public static void registerFlammable(Block blockIn, int encouragement, int flammability) {
        ((FireBlock) Blocks.FIRE).setFireInfo(blockIn, encouragement, flammability);
    }
}
