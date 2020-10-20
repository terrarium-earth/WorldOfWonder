package net.msrandom.worldofwonder.compat;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.*;
import net.msrandom.worldofwonder.client.WonderClientHandler;
import net.msrandom.worldofwonder.client.renderer.tileentity.WonderItemRenderer;
import net.msrandom.worldofwonder.item.WonderItems;
import net.msrandom.worldofwonder.tileentity.StemChestTileEntity;
import net.msrandom.worldofwonder.tileentity.StemTrappedChestTileEntity;
import net.msrandom.worldofwonder.tileentity.WonderTileEntities;

public class WonderQuarkCompat {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, WorldOfWonder.MOD_ID);
    public static final Block STEM_LADDER = add("stem_ladder", new WonderLadderBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.4F).notSolid().sound(SoundType.LADDER)), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final Block STEM_BOOKSHELF = add("stem_bookshelf", new WonderBookshelfBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block STEM_VERTICAL_SLAB = add("stem_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));

    public static final Block STEM_CHEST_BLOCK = add("stem_chest", new StemChestBlock(), WonderClientHandler.getWithRenderer(new Item.Properties().group(ItemGroup.DECORATIONS), () -> () -> new WonderItemRenderer(WonderQuarkCompat.STEM_CHEST_ENTITY)));
    public static final Block STEM_TRAPPED_CHEST_BLOCK = add("stem_trapped_chest", new StemTrappedChestBlock(), WonderClientHandler.getWithRenderer(new Item.Properties().group(ItemGroup.REDSTONE), () -> () -> new WonderItemRenderer(WonderQuarkCompat.STEM_TRAPPED_CHEST_ENTITY)));
    public static final TileEntityType<StemChestTileEntity> STEM_CHEST_ENTITY = WonderTileEntities.add("stem_chest", StemChestTileEntity::new, STEM_CHEST_BLOCK);
    public static final TileEntityType<StemTrappedChestTileEntity> STEM_TRAPPED_CHEST_ENTITY = WonderTileEntities.add("stem_trapped_chest", StemTrappedChestTileEntity::new, STEM_TRAPPED_CHEST_BLOCK);

    public static <T extends Block> T add(String name, T block, Item.Properties properties) {
        REGISTRY.register(name, () -> block);
        WonderItems.REGISTRY.register(name, () -> new BlockItem(block, properties));
        return block;
    }
}
