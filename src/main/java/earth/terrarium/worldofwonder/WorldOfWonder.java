package earth.terrarium.worldofwonder;

import earth.terrarium.worldofwonder.block.WonderBlocks;
import earth.terrarium.worldofwonder.client.WonderClientHandler;
import earth.terrarium.worldofwonder.compat.WonderQuarkCompat;
import earth.terrarium.worldofwonder.compat.WonderVanillaCompat;
import earth.terrarium.worldofwonder.entity.WonderEntities;
import earth.terrarium.worldofwonder.item.WonderItems;
import earth.terrarium.worldofwonder.tileentity.WonderTileEntities;
import earth.terrarium.worldofwonder.world.biome.WonderBiomes;
import earth.terrarium.worldofwonder.world.gen.foliage.WonderFoliagePlacers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.versions.forge.ForgeVersion;

@Mod(WorldOfWonder.MOD_ID)
public class WorldOfWonder {
    public static final TagKey<Item> DANDELION = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ForgeVersion.MOD_ID, "dandelion"));
    public static final String MOD_ID = "worldofwonder";
    public static final WoodType STEM_WOOD_TYPE = WoodType.create(WorldOfWonder.MOD_ID + ":stem");
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
    }
}
