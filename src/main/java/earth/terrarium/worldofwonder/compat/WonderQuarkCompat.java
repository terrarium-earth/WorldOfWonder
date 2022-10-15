package earth.terrarium.worldofwonder.compat;

import earth.terrarium.worldofwonder.WorldOfWonder;
import earth.terrarium.worldofwonder.block.VerticalSlabBlock;
import earth.terrarium.worldofwonder.client.renderer.tileentity.WonderItemRenderer;
import earth.terrarium.worldofwonder.tileentity.StemChestTileEntity;
import earth.terrarium.worldofwonder.tileentity.StemTrappedChestTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import earth.terrarium.worldofwonder.block.StemChestBlock;
import earth.terrarium.worldofwonder.block.StemTrappedChestBlock;
import earth.terrarium.worldofwonder.block.WonderBookshelfBlock;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class WonderQuarkCompat {
    public static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, WorldOfWonder.MOD_ID);
    public static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, WorldOfWonder.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> ENTITY_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WorldOfWonder.MOD_ID);

    public static final RegistryObject<Block> STEM_LADDER = add("stem_ladder", () -> new LadderBlock(Block.Properties.of(Material.DECORATION).strength(0.4F).noOcclusion().sound(SoundType.LADDER)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> STEM_BOOKSHELF = add("stem_bookshelf", () -> new WonderBookshelfBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> STEM_VERTICAL_SLAB = add("stem_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> STEM_CHEST_BLOCK = add("stem_chest", StemChestBlock::new, CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> STEM_TRAPPED_CHEST_BLOCK = add("stem_trapped_chest", StemTrappedChestBlock::new, CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<BlockEntityType<StemChestTileEntity>> STEM_CHEST_ENTITY = ENTITY_REGISTER.register("stem_chest", () -> BlockEntityType.Builder.of(StemChestTileEntity::new, STEM_CHEST_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<StemTrappedChestTileEntity>> STEM_TRAPPED_CHEST_ENTITY = ENTITY_REGISTER.register("stem_trapped_chest", () -> BlockEntityType.Builder.of(StemTrappedChestTileEntity::new, STEM_TRAPPED_CHEST_BLOCK.get()).build(null));

    public static <T extends Block> RegistryObject<T> add(String name, Supplier<T> block, CreativeModeTab tab) {
        final RegistryObject<T> registryObject = BLOCK_REGISTER.register(name, block);
        ITEM_REGISTER.register(name, () -> new BlockItem(registryObject.get(), ModList.get().isLoaded("quark") ? new Item.Properties().tab(tab) : new Item.Properties()));
        return registryObject;
    }
}
