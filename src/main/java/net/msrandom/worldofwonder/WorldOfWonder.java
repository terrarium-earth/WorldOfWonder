package net.msrandom.worldofwonder;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
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
import net.msrandom.worldofwonder.world.gen.foliage.WonderFoliagePlacers;

import java.util.Optional;
import java.util.function.Supplier;

@Mod(WorldOfWonder.MOD_ID)
public class WorldOfWonder {
    @SuppressWarnings("unchecked")
    public static final Tags.IOptionalNamedTag<Item> DANDELION = ItemTags.createOptional(new ResourceLocation("dandelion"), Sets.newHashSet(() -> Items.DANDELION));
    public static final String MOD_ID = "worldofwonder";
    public static final SimpleChannel NETWORK = INetworkPacket.makeChannel("network", "1");
    public static boolean quarkLoaded;
    private static int currentNetworkId;

    public WorldOfWonder() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        if (quarkLoaded = ModList.get().isLoaded("quark")) WonderQuarkCompat.REGISTRY.register(bus);
        WonderBlocks.REGISTRY.register(bus);
        WonderItems.REGISTRY.register(bus);
        WonderBiomes.REGISTRY.register(bus);
        WonderEntities.REGISTRY.register(bus);
        WonderTileEntities.REGISTRY.register(bus);
        WonderFoliagePlacers.REGISTRY.register(bus);

        bus.addGenericListener(Biome.class, WonderBiomes::init);
        bus.addGenericListener(EntityType.class, WonderEntities::init);
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
            context.enqueueWork(() -> msg.handle(context.getDirection().getOriginationSide().isServer() ? getClientPlayer() : context.getSender()));
            context.setPacketHandled(true);
        }, Optional.of(side.isClient() ? NetworkDirection.PLAY_TO_CLIENT : NetworkDirection.PLAY_TO_SERVER));
    }

    @OnlyIn(Dist.CLIENT)
    private static PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
