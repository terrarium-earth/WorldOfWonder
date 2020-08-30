package net.msrandom.worldofwonder;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.msrandom.worldofwonder.block.WonderBlocks;
import net.msrandom.worldofwonder.client.WonderClientHandler;
import net.msrandom.worldofwonder.compat.WonderQuarkCompat;
import net.msrandom.worldofwonder.compat.WonderVanillaCompat;
import net.msrandom.worldofwonder.entity.WonderEntities;
import net.msrandom.worldofwonder.item.WonderItems;
import net.msrandom.worldofwonder.network.INetworkPacket;
import net.msrandom.worldofwonder.network.OpenStemSignPacket;
import net.msrandom.worldofwonder.network.UpdateSignPacket;
import net.msrandom.worldofwonder.tileentity.WonderTileEntities;
import net.msrandom.worldofwonder.world.biome.WonderBiomes;
import net.msrandom.worldofwonder.world.gen.feature.WonderFeatures;

import java.util.Optional;
import java.util.function.Supplier;

@Mod(WorldOfWonder.MOD_ID)
public class WorldOfWonder {
    public static final String MOD_ID = "worldofwonder";
    public static final SimpleChannel NETWORK = INetworkPacket.makeChannel("network", "1");
    public static boolean quarkLoaded;
    private static int currentNetworkId;

    public WorldOfWonder() {
        quarkLoaded = true;//ModList.get().isLoaded("quark");;
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

        bus.addListener(WonderClientHandler::init);

        registerMessage(OpenStemSignPacket.class, OpenStemSignPacket::new, LogicalSide.CLIENT);
        registerMessage(UpdateSignPacket.class, UpdateSignPacket::new, LogicalSide.SERVER);
    }

    private <T extends INetworkPacket> void registerMessage(Class<T> message, Supplier<T> supplier, LogicalSide side) {
        NETWORK.registerMessage(currentNetworkId++, message, INetworkPacket::write, buffer -> {
            T msg = supplier.get();
            msg.read(buffer);
            return msg;
        }, (msg, contextSupplier) -> {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> msg.handle(context.getDirection() == NetworkDirection.PLAY_TO_CLIENT ? getClientPlayer() : context.getSender()));
            context.setPacketHandled(true);
        }, Optional.of(side.isClient() ? NetworkDirection.PLAY_TO_CLIENT : NetworkDirection.PLAY_TO_SERVER));
    }

    @OnlyIn(Dist.CLIENT)
    private static PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
