package net.msrandom.worldofwonder.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.item.StemSignItem;
import net.msrandom.worldofwonder.item.WonderItems;

import java.util.function.BiFunction;

public class WonderBlocks {
    public static final DeferredRegister<Block> REGISTRY = new DeferredRegister<>(ForgeRegistries.BLOCKS, WorldOfWonder.MOD_ID);
    public static final Block DANDELION_FLUFF = add("dandelion_fluff", new BlockDandelionFluff(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final Block STEM_PLANKS = add("stem_planks", new Block(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block STEM_LADDER = add("stem_ladder", new WonderLadderBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.4F).notSolid().sound(SoundType.LADDER)), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final WonderBookshelfBlock STEM_BOOKSHELF = add("stem_bookshelf", new WonderBookshelfBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    private static final MaterialColor STEM_PLANKS_COLOR = STEM_PLANKS.getDefaultState().getMaterial().getColor();
    public static final Block STEM_LOG = add("stem_log", new LogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD, MaterialColor.GREEN).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block STRIPPED_STEM_LOG = add("stripped_stem_log", new LogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block STEM_WOOD = add("stem_wood", new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block STRIPPED_STEM_WOOD = add("stripped_stem_wood", new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block DANDELION_PETALS = add("dandelion_petals", new Block(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).sound(SoundType.PLANT)), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final Block STEM_STAIRS = add("stem_stairs", new StairsBlock(STEM_PLANKS::getDefaultState, Block.Properties.from(STEM_PLANKS)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block STEM_SIGN = add("stem_sign", new StemStandingSignBlock(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD)), new Item.Properties().maxStackSize(16).group(ItemGroup.DECORATIONS), (block, properties) -> new StemSignItem(properties, block, WonderBlocks.STEM_WALL_SIGN));
    public static final Block STEM_DOOR = add("stem_door", new WonderDoorBlock(Block.Properties.create(Material.WOOD, STEM_PLANKS_COLOR).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()), new Item.Properties().group(ItemGroup.REDSTONE));
    public static final Block STEM_WALL_SIGN = add("stem_wall_sign", new StemWallSignBlock(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD).lootFrom(STEM_SIGN)));
    public static final Block STEM_PRESSURE_PLATE = add("stem_pressure_plate", new WonderPressurePlateBlock(Block.Properties.create(Material.WOOD, STEM_PLANKS_COLOR).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.REDSTONE));
    public static final Block STEM_FENCE = add("stem_fence", new FenceBlock(Block.Properties.create(Material.WOOD, STEM_PLANKS_COLOR).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final Block STEM_TRAPDOOR = add("stem_trapdoor", new WonderTrapDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(3.0F).notSolid().sound(SoundType.WOOD).tickRandomly()), new Item.Properties().group(ItemGroup.REDSTONE));
    public static final Block STEM_FENCE_GATE = add("stem_fence_gate", new FenceGateBlock(Block.Properties.create(Material.WOOD, STEM_PLANKS_COLOR).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.REDSTONE));
    public static final Block STEM_BUTTON = add("stem_button", new WonderWoodButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.REDSTONE));
    public static final Block STEM_SLAB = add("stem_slab", new SlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block STEM_VERTICAL_SLAB = add("stem_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block DANDE_LION_SPROUT = add("dande_lion_sprout", new DandeLionSproutBlock(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final Block POTTED_DANDE_LION_SPROUT = add("potted_dande_lion_sprout", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> WonderBlocks.POTTED_DANDE_LION_SPROUT, Block.Properties.create(Material.MISCELLANEOUS).notSolid()));

    //Register a block without an item, add("name", new Block(...))
    private static <T extends Block> T add(String name, T block) {
        return add(name, block, null);
    }

    //Register a block with an item, add("name", new Block(...), new Item.Properties())
    private static <T extends Block> T add(String name, T block, Item.Properties properties) {
        return add(name, block, properties, BlockItem::new);
    }
    //Register a block with a custom item, add("name", new Block(...), new Item.Properties(), ItemName::new)
    private static <T extends Block> T add(String name, T block, Item.Properties properties, BiFunction<Block, Item.Properties, BlockItem> itemConstructor) {
        REGISTRY.register(name, () -> block);
        if (itemConstructor != null && properties != null) WonderItems.REGISTRY.register(name, () -> itemConstructor.apply(block, properties));
        return block;
    }
}

