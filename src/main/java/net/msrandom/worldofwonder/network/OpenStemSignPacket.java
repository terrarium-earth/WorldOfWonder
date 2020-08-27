package net.msrandom.worldofwonder.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.msrandom.worldofwonder.client.gui.screen.EditStemSignScreen;
import net.msrandom.worldofwonder.tileentity.StemSignTileEntity;

public class OpenStemSignPacket implements INetworkPacket {
    private BlockPos pos;

    public OpenStemSignPacket() {}

    public OpenStemSignPacket(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void read(PacketBuffer buffer) {
        pos = buffer.readBlockPos();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeBlockPos(pos);
    }

    @Override
    public void handle(PlayerEntity player) {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().displayGuiScreen(new EditStemSignScreen((StemSignTileEntity) player.world.getTileEntity(pos))));
    }
}
