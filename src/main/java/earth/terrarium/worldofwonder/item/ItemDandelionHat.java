package earth.terrarium.worldofwonder.item;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import earth.terrarium.worldofwonder.WorldOfWonder;
import earth.terrarium.worldofwonder.client.renderer.entity.model.DandelionHatModel;

import javax.annotation.Nullable;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemDandelionHat extends ArmorItem {
    public static final ArmorMaterial MATERIAL = new WonderArmorMaterial(WorldOfWonder.MOD_ID + ":dandelion", 1, new int[]{1, 2, 3, 1}, 3, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0, () -> Ingredient.of(WorldOfWonder.DANDELION));

    public ItemDandelionHat() {
        super(MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (player.isAlive() && player.isOnGround()) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 130, 0, false, false, true));
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
        return (A) DandelionHatModel.INSTANCE;
    }
}
