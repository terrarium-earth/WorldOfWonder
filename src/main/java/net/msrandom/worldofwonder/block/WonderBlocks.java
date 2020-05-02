package net.msrandom.worldofwonder.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.item.WonderItems;

import java.util.function.BiFunction;

public class WonderBlocks {
    public static final DeferredRegister<Block> REGISTRY = new DeferredRegister<>(ForgeRegistries.BLOCKS, WorldOfWonder.MOD_ID);
    public static final Block DANDELION_FLUFF = add("dandelion_fluff", new Block(Block.Properties.create(Material.PLANTS)), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final Block STEM_PLANKS = add("stem_planks", new Block(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    private static final MaterialColor STEM_PLANKS_COLOR = STEM_PLANKS.getDefaultState().getMaterial().getColor();
    public static final Block STRIPPED_STEM_WOOD = add("stripped_stem_wood", new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block STEM_WOOD = add("stem_wood", new Block(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block STEM_LEAVES = add("stem_leaves", new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).func_226896_b_()), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final Block STEM_STAIRS = add("stem_stairs", new StairsBlock(STEM_PLANKS::getDefaultState, Block.Properties.from(STEM_PLANKS)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    public static final Block STEM_SIGN = add("stem_sign", new StandingSignBlock(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), WoodType.field_227038_a_), new Item.Properties().maxStackSize(16).group(ItemGroup.DECORATIONS),(block, properties) -> new SignItem(properties, block, WonderBlocks.STEM_WALL_SIGN));
    public static final Block STEM_DOOR = add("stem_door", new WonderDoorBlock(Block.Properties.create(Material.WOOD, STEM_PLANKS_COLOR).hardnessAndResistance(3.0F).sound(SoundType.WOOD).func_226896_b_()), new Item.Properties().group(ItemGroup.REDSTONE));
    public static final Block STEM_WALL_SIGN = add("stem_wall_sign", new WallSignBlock(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD).lootFrom(STEM_SIGN), WoodType.field_227038_a_), null, null);
    public static final Block STEM_PRESSURE_PLATE = add("stem_pressure_plate", new WonderPressurePlateBlock(Block.Properties.create(Material.WOOD, STEM_PLANKS_COLOR).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.REDSTONE));
    public static final Block STEM_FENCE = add("stem_fence", new FenceBlock(Block.Properties.create(Material.WOOD, STEM_PLANKS_COLOR).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final Block STEM_TRAPDOOR = add("stem_trapdoor", new WonderTrapDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD).func_226896_b_()), new Item.Properties().group(ItemGroup.REDSTONE));
    public static final Block STEM_FENCE_GATE = add("stem_fence_gate", new FenceGateBlock(Block.Properties.create(Material.WOOD, STEM_PLANKS_COLOR).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.REDSTONE));
    public static final Block STEM_BUTTON = add("stem_button", new WonderWoodButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.REDSTONE));
    public static final Block STEM_SLAB = add("stem_slab", new SlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));

    //Maybe not needed
    private static <T extends Block> T add(String name, T block) {
        return add(name, block, new Item.Properties());
    }

    private static <T extends Block> T add(String name, T block, Item.Properties properties) {
        return add(name, block, properties, BlockItem::new);
    }

    private static <T extends Block> T add(String name, T block, Item.Properties properties, BiFunction<Block, Item.Properties, BlockItem> itemConstructor) {
        REGISTRY.register(name, () -> block);
        if (itemConstructor != null) {
            BlockItem item = itemConstructor.apply(block, properties);
            WonderItems.REGISTRY.register(name, () -> item);
        }
        return block;
    }
}
