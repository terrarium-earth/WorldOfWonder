package earth.terrarium.worldofwonder.network;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import earth.terrarium.worldofwonder.client.WonderClientHandler;

public class OpenStemSignPacket implements INetworkPacket {
    private BlockPos pos;

    public OpenStemSignPacket() {}

    public OpenStemSignPacket(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    @Override
    public void handle(Player player) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> WonderClientHandler.openSignEditScreen(player, pos));
    }
}
