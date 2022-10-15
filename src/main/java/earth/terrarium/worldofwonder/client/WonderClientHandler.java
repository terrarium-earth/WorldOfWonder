package earth.terrarium.worldofwonder.client;

import earth.terrarium.worldofwonder.block.WonderBlocks;
import earth.terrarium.worldofwonder.client.renderer.entity.DandeLionRenderer;
import earth.terrarium.worldofwonder.client.renderer.entity.DandeLionSeedRenderer;
import earth.terrarium.worldofwonder.client.renderer.entity.StemBoatRenderer;
import earth.terrarium.worldofwonder.client.renderer.tileentity.StemChestTileEntityRenderer;
import earth.terrarium.worldofwonder.compat.WonderQuarkCompat;
import earth.terrarium.worldofwonder.entity.WonderEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Function;

public class WonderClientHandler {
    public static void init(FMLClientSetupEvent event) {
        EntityRenderers.register(WonderEntities.STEM_BOAT.get(), StemBoatRenderer::new);
        EntityRenderers.register(WonderEntities.DANDE_LION.get(), DandeLionRenderer::new);
        EntityRenderers.register(WonderEntities.DANDE_LION_SEED.get(), DandeLionSeedRenderer::new);

        ItemBlockRenderTypes.setRenderLayer(WonderBlocks.STEM_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(WonderBlocks.STEM_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(WonderBlocks.DANDE_LION_SPROUT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(WonderBlocks.POTTED_DANDE_LION_SPROUT.get(), RenderType.cutout());

        BlockEntityRenderers.register(WonderQuarkCompat.STEM_CHEST_ENTITY.get(), StemChestTileEntityRenderer::new);
        BlockEntityRenderers.register(WonderQuarkCompat.STEM_TRAPPED_CHEST_ENTITY.get(), StemChestTileEntityRenderer::new);
        ItemBlockRenderTypes.setRenderLayer(WonderQuarkCompat.STEM_LADDER.get(), RenderType.cutout());
    }
}
