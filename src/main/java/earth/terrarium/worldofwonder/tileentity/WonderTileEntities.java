package earth.terrarium.worldofwonder.tileentity;

import earth.terrarium.worldofwonder.WorldOfWonder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import earth.terrarium.worldofwonder.block.WonderBlocks;

@SuppressWarnings("ConstantConditions")
public class WonderTileEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, WorldOfWonder.MOD_ID);
    public static final RegistryObject<BlockEntityType<StemSignTileEntity>> STEM_SIGN = REGISTRY.register("stem_sign", () -> BlockEntityType.Builder.of(StemSignTileEntity::new, WonderBlocks.STEM_SIGN.get(), WonderBlocks.STEM_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<DandeLionSproutTileEntity>> DANDE_LION_SPROUT = REGISTRY.register("dande_lion_sprout", () -> BlockEntityType.Builder.of(DandeLionSproutTileEntity::new, WonderBlocks.DANDE_LION_SPROUT.get()).build(null));
}
