package earth.terrarium.worldofwonder;

import com.google.common.collect.Sets;
import earth.terrarium.worldofwonder.item.WonderItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
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
import earth.terrarium.worldofwonder.block.WonderBlocks;
import earth.terrarium.worldofwonder.client.WonderClientHandler;
import earth.terrarium.worldofwonder.compat.WonderQuarkCompat;
import earth.terrarium.worldofwonder.compat.WonderVanillaCompat;
import earth.terrarium.worldofwonder.entity.WonderEntities;
import earth.terrarium.worldofwonder.network.INetworkPacket;
import earth.terrarium.worldofwonder.network.OpenStemSignPacket;
import earth.terrarium.worldofwonder.network.UpdateSignPacket;
import earth.terrarium.worldofwonder.tileentity.WonderTileEntities;
import earth.terrarium.worldofwonder.world.biome.WonderBiomes;
import earth.terrarium.worldofwonder.world.gen.foliage.WonderFoliagePlacers;

import java.util.Optional;
import java.util.function.Supplier;

@Mod(WorldOfWonder.MOD_ID)
public class WorldOfWonder {
    @SuppressWarnings("unchecked")
    public static final Tags.IOptionalNamedTag<Item> DANDELION = ItemTags.createOptional(new ResourceLocation("dandelion"), Sets.newHashSet(() -> Items.DANDELION));
    public static final String MOD_ID = "worldofwonder";
    public static final SimpleChannel NETWORK = INetworkPacket.makeChannel("network", "1");
    private static int currentNetworkId;

    public WorldOfWonder() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        WonderQuarkCompat.BLOCK_REGISTER.register(bus);
        WonderQuarkCompat.ITEM_REGISTER.register(bus);
        WonderQuarkCompat.ENTITY_REGISTER.register(bus);

        WonderBlocks.REGISTRY.register(bus);
        WonderItems.REGISTRY.register(bus);
        WonderBiomes.REGISTRY.register(bus);
        WonderSounds.REGISTRY.register(bus);
        WonderEntities.REGISTRY.register(bus);
        WonderTileEntities.REGISTRY.register(bus);
        WonderFoliagePlacers.REGISTRY.register(bus);

        bus.addListener(WonderEntities::setupAttributes);
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
    private static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
