package net.msrandom.worldofwonder.network;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.msrandom.worldofwonder.tileentity.StemSignTileEntity;

import java.util.Arrays;

public class UpdateSignPacket implements INetworkPacket {
    private BlockPos pos;
    private String[] lines;

    public UpdateSignPacket() {
    }

    public UpdateSignPacket(BlockPos pos, ITextComponent[] text) {
        this.pos = pos;
        this.lines = Arrays.stream(text).map(ITextComponent::getString).toArray(String[]::new);
    }

    @Override
    public void read(PacketBuffer buffer) {
        this.pos = buffer.readBlockPos();
        this.lines = new String[4];

        for (int i = 0; i < 4; ++i) {
            this.lines[i] = buffer.readString(384);
        }
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeBlockPos(this.pos);

        for (int i = 0; i < 4; ++i) {
            buffer.writeString(this.lines[i]);
        }
    }

    @Override
    public void handle(PlayerEntity player) {
        ((ServerPlayerEntity) player).markPlayerActive();
        ServerWorld serverworld = (ServerWorld) player.world;
        if (serverworld.isAreaLoaded(pos, 16)) {
            BlockState blockstate = serverworld.getBlockState(pos);
            TileEntity tileentity = serverworld.getTileEntity(pos);
            if (!(tileentity instanceof StemSignTileEntity)) {
                return;
            }

            StemSignTileEntity signtileentity = (StemSignTileEntity)tileentity;
            if (signtileentity.isEditable() && signtileentity.getPlayer() == player) {
                String[] astring = lines;

                for (int i = 0; i < astring.length; ++i) {
                    signtileentity.setText(i, new StringTextComponent(TextFormatting.getTextWithoutFormattingCodes(astring[i])));
                }

                signtileentity.markDirty();
                serverworld.notifyBlockUpdate(pos, blockstate, blockstate, 3);
            }
        }
    }
}
