package net.msrandom.worldofwonder.tileentity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.Function;

public class StemSignTileEntity extends TileEntity {
    public final ITextComponent[] signText = new ITextComponent[] {new StringTextComponent(""), new StringTextComponent(""), new StringTextComponent(""), new StringTextComponent("")};
    private boolean isEditable = true;
    private PlayerEntity player;
    private final IReorderingProcessor[] renderText = new IReorderingProcessor[4];
    private DyeColor textColor = DyeColor.BLACK;

    public StemSignTileEntity() {
        super(WonderTileEntities.STEM_SIGN.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        for(int i = 0; i < 4; ++i) {
            String s = ITextComponent.Serializer.toJson(this.signText[i]);
            compound.putString("Text" + (i + 1), s);
        }

        compound.putString("Color", this.textColor.getName());
        return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        this.isEditable = false;
        super.load(state, compound);
        this.textColor = DyeColor.byName(compound.getString("Color"), DyeColor.BLACK);

        for(int i = 0; i < 4; ++i) {
            String s = compound.getString("Text" + (i + 1));
            ITextComponent itextcomponent = ITextComponent.Serializer.fromJson(s.isEmpty() ? "\"\"" : s);
            if (this.level instanceof ServerWorld) {
                try {
                    this.signText[i] = TextComponentUtils.updateForEntity(this.getCommandSource(null), itextcomponent, null, 0);
                } catch (CommandSyntaxException var6) {
                    this.signText[i] = itextcomponent;
                }
            } else {
                this.signText[i] = itextcomponent;
            }

            this.renderText[i] = null;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public ITextComponent getText(int line) {
        return this.signText[line];
    }

    public void setText(int line, ITextComponent p_212365_2_) {
        this.signText[line] = p_212365_2_;
        this.renderText[line] = null;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public IReorderingProcessor getRenderText(int line, Function<ITextComponent, IReorderingProcessor> p_212364_2_) {
        if (this.renderText[line] == null && this.signText[line] != null) {
            this.renderText[line] = p_212364_2_.apply(this.signText[line]);
        }

        return this.renderText[line];
    }

    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, -1, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public boolean isEditable() {
        return this.isEditable;
    }

    @OnlyIn(Dist.CLIENT)
    public void setEditable(boolean isEditableIn) {
        this.isEditable = isEditableIn;
        if (!isEditableIn) {
            this.player = null;
        }
    }

    public void setPlayer(PlayerEntity playerIn) {
        this.player = playerIn;
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }

    public boolean executeCommand(PlayerEntity playerIn) {
        for(ITextComponent itextcomponent : this.signText) {
            Style style = itextcomponent == null ? null : itextcomponent.getStyle();
            if (style != null && style.getClickEvent() != null) {
                ClickEvent clickevent = style.getClickEvent();
                if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    playerIn.getServer().getCommands().performCommand(this.getCommandSource((ServerPlayerEntity)playerIn), clickevent.getValue());
                }
            }
        }

        return true;
    }

    public CommandSource getCommandSource(@Nullable ServerPlayerEntity playerIn) {
        String s = playerIn == null ? "Sign" : playerIn.getName().getString();
        ITextComponent itextcomponent = playerIn == null ? new StringTextComponent("Sign") : playerIn.getDisplayName();
        return new CommandSource(ICommandSource.NULL, new Vector3d((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D), Vector2f.ZERO, (ServerWorld)this.level, 2, s, itextcomponent, this.level.getServer(), playerIn);
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
