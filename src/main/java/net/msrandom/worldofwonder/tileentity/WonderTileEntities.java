package net.msrandom.worldofwonder.tileentity;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.WonderBlocks;

import java.util.function.Supplier;

public class WonderTileEntities {
    public static final DeferredRegister<TileEntityType<?>> REGISTRY = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, WorldOfWonder.MOD_ID);
    public static final TileEntityType<StemSignTileEntity> STEM_SIGN = add("stem_sign", StemSignTileEntity::new, WonderBlocks.STEM_SIGN, WonderBlocks.STEM_WALL_SIGN);
    public static final TileEntityType<DandeLionSproutTileEntity> DANDE_LION_SPROUT = add("dande_lion_sprout", DandeLionSproutTileEntity::new, WonderBlocks.DANDE_LION_SPROUT, WonderBlocks.POTTED_DANDE_LION_SPROUT);

    public static <T extends TileEntity> TileEntityType<T> add(String name, Supplier<T> factory, Block... blocks) {
        @SuppressWarnings("ConstantConditions") TileEntityType<T> type = TileEntityType.Builder.create(factory, blocks).build(null);
        REGISTRY.register(name, () -> type);
        return type;
    }
}
