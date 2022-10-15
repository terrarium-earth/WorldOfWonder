package earth.terrarium.worldofwonder.tileentity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.function.Function;

public class StemSignTileEntity extends BlockEntity {
    public final Component[] signText = new Component[] {Component.literal(""), Component.literal(""), Component.literal(""), Component.literal("")};
    private boolean isEditable = true;
    private Player player;
    private final FormattedCharSequence[] renderText = new FormattedCharSequence[4];
    private DyeColor textColor = DyeColor.BLACK;

    public StemSignTileEntity(BlockPos pos, BlockState state) {
        super(WonderTileEntities.STEM_SIGN.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        for (int i = 0; i < 4; ++i) {
            String s = Component.Serializer.toJson(this.signText[i]);
            tag.putString("Text" + (i + 1), s);
        }

        tag.putString("Color", this.textColor.getName());
    }

    @Override
    public void load(CompoundTag tag) {
        this.isEditable = false;
        super.load(tag);
        this.textColor = DyeColor.byName(tag.getString("Color"), DyeColor.BLACK);

        for (int i = 0; i < 4; ++i) {
            String s = tag.getString("Text" + (i + 1));
            Component component = Component.Serializer.fromJson(s.isEmpty() ? "\"\"" : s);
            if (this.level instanceof ServerLevel) {
                try {
                    this.signText[i] = ComponentUtils.updateForEntity(this.getCommandSource(null), component, null, 0);
                } catch (CommandSyntaxException var6) {
                    this.signText[i] = component;
                }
            } else {
                this.signText[i] = component;
            }

            this.renderText[i] = null;
        }
    }

    public Component getText(int line) {
        return this.signText[line];
    }

    public void setText(int line, Component p_212365_2_) {
        this.signText[line] = p_212365_2_;
        this.renderText[line] = null;
    }

    @Nullable
    public FormattedCharSequence getRenderText(int line, Function<Component, FormattedCharSequence> p_212364_2_) {
        if (this.renderText[line] == null && this.signText[line] != null) {
            this.renderText[line] = p_212364_2_.apply(this.signText[line]);
        }

        return this.renderText[line];
    }

    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public boolean isEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean isEditableIn) {
        this.isEditable = isEditableIn;
        if (!isEditableIn) {
            this.player = null;
        }
    }

    public void setPlayer(Player playerIn) {
        this.player = playerIn;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean executeCommand(Player playerIn) {
        for (Component itextcomponent : this.signText) {
            Style style = itextcomponent == null ? null : itextcomponent.getStyle();
            if (style != null && style.getClickEvent() != null) {
                ClickEvent clickevent = style.getClickEvent();
                if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    playerIn.getServer().getCommands().performPrefixedCommand(this.getCommandSource((ServerPlayer) playerIn), clickevent.getValue());
                }
            }
        }

        return true;
    }

    public CommandSourceStack getCommandSource(@Nullable ServerPlayer playerIn) {
        String s = playerIn == null ? "Sign" : playerIn.getName().getString();
        Component itextcomponent = playerIn == null ? Component.literal("Sign") : playerIn.getDisplayName();
        return new CommandSourceStack(CommandSource.NULL, new Vec3((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D), Vec2.ZERO, (ServerLevel) this.level, 2, s, itextcomponent, this.level.getServer(), playerIn);
    }

    public DyeColor getTextColor() {
        return this.textColor;
    }

    public boolean setTextColor(DyeColor newColor) {
        if (newColor != this.getTextColor()) {
            this.textColor = newColor;
            this.setChanged();
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
            return true;
        } else {
            return false;
        }
    }
}
