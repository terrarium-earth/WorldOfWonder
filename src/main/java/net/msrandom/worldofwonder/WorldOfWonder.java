package net.msrandom.worldofwonder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.client.renderer.entity.DandeLionRenderer;
import net.msrandom.worldofwonder.client.renderer.entity.DandeLionSeedRenderer;
import net.msrandom.worldofwonder.client.renderer.entity.StemBoatRenderer;
import net.msrandom.worldofwonder.client.renderer.tileentity.StemChestTileEntityRenderer;
import net.msrandom.worldofwonder.client.renderer.tileentity.StemSignTileEntityRenderer;
import net.msrandom.worldofwonder.compat.WonderQuarkCompat;
import net.msrandom.worldofwonder.compat.WonderVanillaCompat;
import net.msrandom.worldofwonder.entity.WonderEntities;
import net.msrandom.worldofwonder.item.WonderItems;
import net.msrandom.worldofwonder.network.INetworkPacket;
import net.msrandom.worldofwonder.network.OpenStemSignPacket;
import net.msrandom.worldofwonder.tileentity.WonderTileEntities;
import net.msrandom.worldofwonder.world.biome.WonderBiomes;
import net.msrandom.worldofwonder.world.gen.feature.WonderFeatures;

import java.util.function.Function;
import java.util.function.Supplier;

@Mod(WorldOfWonder.MOD_ID)
public class WorldOfWonder {
    private static final String NETWORK_VERSION = "1";
    public static final String MOD_ID = "worldofwonder";
    public static final SimpleChannel NETWORK = INetworkPacket.makeChannel("network", "1");
    private static boolean quarkLoaded;
    private static int currentNetworkId;

    public WorldOfWonder() {
        quarkLoaded = ModList.get().isLoaded("quark");;
        if (quarkLoaded) WonderQuarkCompat.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        WonderBlocks.REGISTRY.register(bus);
        WonderItems.REGISTRY.register(bus);
        WonderBiomes.REGISTRY.register(bus);
        WonderEntities.REGISTRY.register(bus);
        WonderTileEntities.REGISTRY.register(bus);
        WonderFeatures.REGISTRY.register(bus);

        bus.addListener(WonderBiomes::registerTypes);
        bus.addListener(WonderVanillaCompat::init);

        bus.addListener(this::registerClient);

        registerMessage(OpenStemSignPacket.class, OpenStemSignPacket::new);
    }

    private void registerClient(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(WonderEntities.STEM_BOAT, StemBoatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WonderEntities.DANDE_LION, DandeLionRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WonderEntities.DANDE_LION_SEED, DandeLionSeedRenderer::new);
        ClientRegistry.bindTileEntityRenderer(WonderTileEntities.STEM_SIGN, StemSignTileEntityRenderer::new);

        RenderTypeLookup.setRenderLayer(WonderBlocks.STEM_DOOR, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(WonderBlocks.STEM_TRAPDOOR, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(WonderBlocks.DANDE_LION_SPROUT, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(WonderBlocks.POTTED_DANDE_LION_SPROUT, RenderType.getCutout());

        if (quarkLoaded) {
            Function<TileEntityRendererDispatcher, StemChestTileEntityRenderer> chestRenderer = StemChestTileEntityRenderer::new;
            ClientRegistry.bindTileEntityRenderer(WonderQuarkCompat.STEM_CHEST_ENTITY, chestRenderer);
            ClientRegistry.bindTileEntityRenderer(WonderQuarkCompat.STEM_TRAPPED_CHEST_ENTITY, chestRenderer);
            RenderTypeLookup.setRenderLayer(WonderQuarkCompat.STEM_LADDER, RenderType.getCutout());
        }
    }

    private <T extends INetworkPacket> void registerMessage(Class<T> message, Supplier<T> supplier) {
        NETWORK.registerMessage(currentNetworkId++, message, INetworkPacket::write, buffer -> {
            T msg = supplier.get();
            msg.read(buffer);
            return msg;
        }, (msg, ctx) -> {
            ctx.get().enqueueWork(msg::handle);
            ctx.get().setPacketHandled(true);
        });
    }
}
