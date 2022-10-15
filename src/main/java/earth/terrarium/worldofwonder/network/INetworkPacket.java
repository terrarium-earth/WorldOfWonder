package earth.terrarium.worldofwonder.network;

import earth.terrarium.worldofwonder.WorldOfWonder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public interface INetworkPacket {
    static SimpleChannel makeChannel(String name, String version) {
        return NetworkRegistry.newSimpleChannel(new ResourceLocation(WorldOfWonder.MOD_ID, name), () -> version, version::equals, version::equals);
    }

    void read(FriendlyByteBuf buffer);
    void write(FriendlyByteBuf buffer);
    void handle(Player player);
}
