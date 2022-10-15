package earth.terrarium.worldofwonder.client;

import earth.terrarium.worldofwonder.WorldOfWonder;
import earth.terrarium.worldofwonder.client.renderer.entity.DandeLionRenderer;
import earth.terrarium.worldofwonder.client.renderer.entity.DandeLionSeedRenderer;
import earth.terrarium.worldofwonder.client.renderer.tileentity.StemChestTileEntityRenderer;
import earth.terrarium.worldofwonder.client.renderer.tileentity.StemSignTileEntityRenderer;
import earth.terrarium.worldofwonder.compat.WonderQuarkCompat;
import earth.terrarium.worldofwonder.entity.WonderEntities;
import earth.terrarium.worldofwonder.tileentity.StemSignTileEntity;
import earth.terrarium.worldofwonder.tileentity.WonderTileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import earth.terrarium.worldofwonder.block.WonderBlocks;
import earth.terrarium.worldofwonder.client.gui.screen.EditStemSignScreen;
import earth.terrarium.worldofwonder.client.renderer.entity.StemBoatRenderer;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

public class WonderClientHandler {
    public static void init(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(WonderEntities.STEM_BOAT.get(), StemBoatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WonderEntities.DANDE_LION.get(), DandeLionRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WonderEntities.DANDE_LION_SEED.get(), DandeLionSeedRenderer::new);
        ClientRegistry.bindTileEntityRenderer(WonderTileEntities.STEM_SIGN.get(), StemSignTileEntityRenderer::new);

        ItemBlockRenderTypes.setRenderLayer(WonderBlocks.STEM_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(WonderBlocks.STEM_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(WonderBlocks.DANDE_LION_SPROUT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(WonderBlocks.POTTED_DANDE_LION_SPROUT.get(), RenderType.cutout());

        Function<BlockEntityRenderDispatcher, StemChestTileEntityRenderer> chestRenderer = StemChestTileEntityRenderer::new;
        ClientRegistry.bindTileEntityRenderer(WonderQuarkCompat.STEM_CHEST_ENTITY.get(), chestRenderer);
        ClientRegistry.bindTileEntityRenderer(WonderQuarkCompat.STEM_TRAPPED_CHEST_ENTITY.get(), chestRenderer);
        ItemBlockRenderTypes.setRenderLayer(WonderQuarkCompat.STEM_LADDER.get(), RenderType.cutout());
    }

    public static Item.Properties getWithRenderer(Item.Properties properties, Supplier<Callable<Object>> ister) {
        return EffectiveSide.get().isClient() ? properties.setISTER(cast(ister)) : properties;
    }

    @SuppressWarnings("unchecked")
    private static <F, T> T cast(F toCast) {
        return (T) toCast;
    }

    @OnlyIn(Dist.CLIENT)
    public static void openSignEditScreen(Player player, BlockPos pos) {
        Minecraft.getInstance().setScreen(new EditStemSignScreen((StemSignTileEntity) player.level.getBlockEntity(pos)));
    }
}
