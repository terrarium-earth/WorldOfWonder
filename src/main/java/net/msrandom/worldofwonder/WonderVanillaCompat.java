package net.msrandom.worldofwonder;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.util.IItemProvider;
import net.msrandom.worldofwonder.block.WonderBlocks;

public class WonderVanillaCompat {

    public static void setupWonderVanillaCompat() {
        registerCompostable(WonderBlocks.DANDELION_PETALS.asItem(), 0.3F);
        registerCompostable(WonderBlocks.DANDELION_FLUFF.asItem(), 0.3F);

        registerFlammable(WonderBlocks.STEM_LOG, 5, 5);
        registerFlammable(WonderBlocks.STRIPPED_STEM_LOG, 5, 5);
        registerFlammable(WonderBlocks.STEM_WOOD, 5, 5);
        registerFlammable(WonderBlocks.STRIPPED_STEM_WOOD, 5, 5);

        registerFlammable(WonderBlocks.STEM_PLANKS, 5, 20);
        registerFlammable(WonderBlocks.STEM_STAIRS, 5, 20);
        registerFlammable(WonderBlocks.STEM_SLAB, 5, 20);
        registerFlammable(WonderBlocks.STEM_FENCE, 5, 20);
        registerFlammable(WonderBlocks.STEM_FENCE_GATE, 5, 20);
        registerFlammable(WonderBlocks.STEM_VERTICAL_SLAB, 5, 20);
        registerFlammable(WonderBlocks.STEM_BOOKSHELF, 30, 20);

        registerFlammable(WonderBlocks.DANDELION_FLUFF, 30, 60);
        registerFlammable(WonderBlocks.DANDELION_PETALS, 30, 60);
    }

    public static void registerCompostable(IItemProvider itemIn, float chance) {
        ComposterBlock.CHANCES.put(itemIn, chance);
    }

    public static void registerFlammable(Block blockIn, int encouragement, int flammability) {
        ((FireBlock) Blocks.FIRE).setFireInfo(blockIn, encouragement, flammability);
    }
}
