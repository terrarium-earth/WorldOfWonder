package net.msrandom.worldofwonder;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WonderSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WorldOfWonder.MOD_ID);
    public static final SoundEvent DANDE_LION_AMBIENT = add("dande_lion.ambient");
    public static final SoundEvent DANDE_LION_PUR = add("dande_lion.pur");
    public static final SoundEvent DANDE_LION_HURT = add("dande_lion.hurt");
    public static final SoundEvent DANDE_LION_DEATH = add("dande_lion.death");

    private static SoundEvent add(String name) {
        SoundEvent sound = new SoundEvent(new ResourceLocation(WorldOfWonder.MOD_ID, name));
        REGISTRY.register(name.replace('.', '_'), () -> sound);
        return sound;
    }
}
