package earth.terrarium.worldofwonder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WonderSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WorldOfWonder.MOD_ID);
    public static final RegistryObject<SoundEvent> DANDE_LION_AMBIENT = add("dande_lion.ambient");
    public static final RegistryObject<SoundEvent> DANDE_LION_PUR = add("dande_lion.pur");
    public static final RegistryObject<SoundEvent> DANDE_LION_HURT = add("dande_lion.hurt");
    public static final RegistryObject<SoundEvent> DANDE_LION_DEATH = add("dande_lion.death");

    private static RegistryObject<SoundEvent> add(String name) {
        return REGISTRY.register(name, () -> new SoundEvent(new ResourceLocation(WorldOfWonder.MOD_ID, name)));
    }
}
