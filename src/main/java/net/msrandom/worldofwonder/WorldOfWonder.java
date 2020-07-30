package net.msrandom.worldofwonder;

import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.client.renderer.entity.DandeLionRenderer;
import net.msrandom.worldofwonder.client.renderer.entity.DandeLionSeedRenderer;
import net.msrandom.worldofwonder.client.renderer.entity.StemBoatRenderer;
import net.msrandom.worldofwonder.client.renderer.tileentity.StemSignTileEntityRenderer;
import net.msrandom.worldofwonder.entity.WonderEntities;
import net.msrandom.worldofwonder.item.ItemEvents;
import net.msrandom.worldofwonder.item.WonderItems;
import net.msrandom.worldofwonder.tileentity.WonderTileEntities;
import net.msrandom.worldofwonder.world.biome.WonderBiomes;
import net.msrandom.worldofwonder.world.gen.feature.WonderFeatures;

@Mod(WorldOfWonder.MOD_ID)
public class WorldOfWonder {
    public static final String MOD_ID = "worldofwonder";

    public WorldOfWonder() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        WonderBlocks.REGISTRY.register(bus);
        WonderItems.REGISTRY.register(bus);
        WonderBiomes.REGISTRY.register(bus);
        WonderEntities.REGISTRY.register(bus);
        WonderTileEntities.REGISTRY.register(bus);
        WonderFeatures.REGISTRY.register(bus);
        bus.addListener(WonderBiomes::registerTypes);
        bus.addListener(this::registerClient);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation(MOD_ID, "dande_lion_sprout"), () -> WonderBlocks.POTTED_DANDE_LION_SPROUT);
        DispenserBlock.registerDispenseBehavior(WonderItems.BLOOM_MEAL, ItemEvents.BLOOM_MEAL_DISPENSE);
        EntitySpawnPlacementRegistry.register(WonderEntities.DANDE_LION, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, AnimalEntity::canAnimalSpawn);
    }

    private void registerClient(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(WonderEntities.STEM_BOAT, StemBoatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WonderEntities.DANDE_LION, DandeLionRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WonderEntities.DANDE_LION_SEED, DandeLionSeedRenderer::new);
        ClientRegistry.bindTileEntityRenderer(WonderTileEntities.STEM_SIGN, StemSignTileEntityRenderer::new);
        RenderTypeLookup.setRenderLayer(WonderBlocks.STEM_DOOR, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(WonderBlocks.STEM_TRAPDOOR, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(WonderBlocks.STEM_LADDER, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(WonderBlocks.DANDE_LION_SPROUT, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(WonderBlocks.POTTED_DANDE_LION_SPROUT, RenderType.getCutout());
    }
}
