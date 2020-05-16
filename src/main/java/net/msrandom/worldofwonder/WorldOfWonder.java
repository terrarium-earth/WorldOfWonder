package net.msrandom.worldofwonder;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.msrandom.worldofwonder.tileentity.StemSignTileEntity;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.client.renderer.entity.DandeLionRenderer;
import net.msrandom.worldofwonder.client.renderer.entity.StemBoatRenderer;
import net.msrandom.worldofwonder.client.renderer.tileentity.model.StemSignTileEntityRenderer;
import net.msrandom.worldofwonder.entity.WonderEntities;
import net.msrandom.worldofwonder.item.WonderItems;
import net.msrandom.worldofwonder.tileentity.WonderTileEntities;
import net.msrandom.worldofwonder.world.biome.WonderBiomes;
import net.msrandom.worldofwonder.world.gen.feature.WonderFeatures;

@Mod(WorldOfWonder.MOD_ID)
public class WorldOfWonder {
    public static final String MOD_ID = "worldofwonder";
    private static final SidedExecutor SIDED_EXECUTOR;

    public WorldOfWonder() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        WonderBlocks.REGISTRY.register(bus);
        WonderItems.REGISTRY.register(bus);
        WonderBiomes.REGISTRY.register(bus);
        WonderEntities.REGISTRY.register(bus);
        WonderFeatures.REGISTRY.register(bus);
        bus.addListener(WonderBiomes::registerTypes);
        SIDED_EXECUTOR.registerClient();
    }

    static {
        SIDED_EXECUTOR = DistExecutor.runForDist(() -> () -> new SidedExecutor() {
            @Override
            protected void registerClient() {
                super.registerClient();

                //This is only called on the client, register client sided renders and stuff here
                RenderingRegistry.registerEntityRenderingHandler(WonderEntities.STEM_BOAT, StemBoatRenderer::new);
                RenderingRegistry.registerEntityRenderingHandler(WonderEntities.DANDE_LION, DandeLionRenderer::new);
                ClientRegistry.bindTileEntityRenderer(WonderTileEntities.STEM_SIGN, StemSignTileEntityRenderer::new);
            }
        }, () -> SidedExecutor::new);
    }

    public static class SidedExecutor {
        protected void registerClient() {}
    }
}
