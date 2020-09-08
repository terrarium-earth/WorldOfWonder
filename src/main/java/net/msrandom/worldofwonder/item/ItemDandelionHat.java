package net.msrandom.worldofwonder.item;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.client.renderer.entity.model.DandelionHatModel;

import javax.annotation.Nullable;

public class ItemDandelionHat extends ArmorItem {
    public static final IArmorMaterial MATERIAL = new WonderArmorMaterial(WorldOfWonder.MOD_ID + ":dandelion", 1, new int[]{1, 2, 3, 1}, 3, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, () -> Ingredient.fromTag(ItemTags.getCollection().getOrCreate(new ResourceLocation("dandelion"))));

    public ItemDandelionHat() {
        super(MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (player.isAlive() && player.onGround) {
            player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 130, 0, false, false, true));
        }
    }


    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) DandelionHatModel.INSTANCE;
    }
}
