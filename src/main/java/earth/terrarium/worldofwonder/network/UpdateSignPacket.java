package earth.terrarium.worldofwonder.network;

import earth.terrarium.worldofwonder.tileentity.StemSignTileEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerLevel;

import java.util.Arrays;

public class UpdateSignPacket implements INetworkPacket {
    private BlockPos pos;
    private String[] lines;

    public UpdateSignPacket() {
    }

    public UpdateSignPacket(BlockPos pos, Component[] text) {
        this.pos = pos;
        this.lines = Arrays.stream(text).map(Component::getString).toArray(String[]::new);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.pos = buffer.readBlockPos();
        this.lines = new String[4];

        for (int i = 0; i < 4; ++i) {
            this.lines[i] = buffer.readUtf(384);
        }
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.pos);

        for (int i = 0; i < 4; ++i) {
            buffer.writeUtf(this.lines[i]);
        }
    }

    @Override
    public void handle(Player player) {
        ((ServerPlayer) player).resetLastActionTime();
        ServerLevel serverworld = (ServerLevel) player.level;
        if (serverworld.isAreaLoaded(pos, 16)) {
            BlockState blockstate = serverworld.getBlockState(pos);
            BlockEntity tileentity = serverworld.getBlockEntity(pos);
            if (!(tileentity instanceof StemSignTileEntity)) {
                return;
            }

            StemSignTileEntity signtileentity = (StemSignTileEntity)tileentity;
            if (signtileentity.isEditable() && signtileentity.getPlayer() == player) {
                String[] astring = lines;

                for (int i = 0; i < astring.length; ++i) {
                    signtileentity.setText(i, new TextComponent(ChatFormatting.stripFormatting(astring[i])));
                }

                signtileentity.setChanged();
                serverworld.sendBlockUpdated(pos, blockstate, blockstate, 3);
            }
        }
    }
}
