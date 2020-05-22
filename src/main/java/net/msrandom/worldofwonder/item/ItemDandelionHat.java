package net.msrandom.worldofwonder.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.msrandom.worldofwonder.WorldOfWonder;
import net.msrandom.worldofwonder.client.renderer.entity.model.DandelionHatModel;

import javax.annotation.Nullable;
import java.util.UUID;

public class ItemDandelionHat extends ArmorItem {
    public static final IArmorMaterial MATERIAL = new WonderArmorMaterial(WorldOfWonder.MOD_ID + ":dandelion", 1, new int[]{1, 2, 3, 1}, 3, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, () -> Ingredient.fromTag(ItemTags.getCollection().getOrCreate(new ResourceLocation("dandelion"))));
    private static final AttributeModifier SLOW_FALLING = new AttributeModifier(UUID.fromString("A5B6CF2A-2F7C-31EF-9022-7C3E7D5E6ABA"), "Slow falling acceleration reduction", -0.07, AttributeModifier.Operation.ADDITION).setSaved(false);

    public ItemDandelionHat() {
        super(MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        IAttributeInstance gravity = player.getAttribute(LivingEntity.ENTITY_GRAVITY);
        if (player.getMotion().y <= 0.0D && !player.abilities.isFlying) {
            if (!gravity.hasModifier(SLOW_FALLING)) gravity.applyModifier(SLOW_FALLING);
            player.fallDistance = 0.0F;
            player.setMotion(player.getMotion().mul(0.8, 0.6, 0.8));
        } else if (gravity.hasModifier(SLOW_FALLING)) {
            gravity.removeModifier(SLOW_FALLING);
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends net.minecraft.client.renderer.entity.model.BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) DandelionHatModel.INSTANCE;
    }
}
