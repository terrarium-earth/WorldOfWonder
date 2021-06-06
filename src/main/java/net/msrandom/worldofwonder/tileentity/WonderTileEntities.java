package net.msrandom.worldofwonder.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.block.WonderBlocks;

@SuppressWarnings("ConstantConditions")
public class WonderTileEntities {
    public static final DeferredRegister<TileEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, WorldOfWonder.MOD_ID);
    public static final RegistryObject<TileEntityType<StemSignTileEntity>> STEM_SIGN = REGISTRY.register("stem_sign", () -> TileEntityType.Builder.of(StemSignTileEntity::new, WonderBlocks.STEM_SIGN.get(), WonderBlocks.STEM_WALL_SIGN.get()).build(null));
    public static final RegistryObject<TileEntityType<DandeLionSproutTileEntity>> DANDE_LION_SPROUT = REGISTRY.register("dande_lion_sprout", () -> TileEntityType.Builder.of(DandeLionSproutTileEntity::new, WonderBlocks.DANDE_LION_SPROUT.get()).build(null));
}
