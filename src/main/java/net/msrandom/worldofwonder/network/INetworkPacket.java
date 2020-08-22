package net.msrandom.worldofwonder.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.msrandom.worldofwonder.WorldOfWonder;

public interface INetworkPacket {
    static SimpleChannel makeChannel(String name, String version) {
        return NetworkRegistry.newSimpleChannel(new ResourceLocation(WorldOfWonder.MOD_ID, name), () -> version, version::equals, version::equals);
    }

    void read(PacketBuffer buffer);
    void write(PacketBuffer buffer);
    void handle();
}
