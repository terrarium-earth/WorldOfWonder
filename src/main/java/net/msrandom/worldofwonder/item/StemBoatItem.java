package net.msrandom.worldofwonder.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.msrandom.worldofwonder.entity.StemBoatEntity;

import java.util.List;
import java.util.function.Predicate;

public class StemBoatItem extends Item {
   private static final Predicate<Entity> field_219989_a = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);

   public StemBoatItem() {
      super(new Item.Properties().maxStackSize(1).group(ItemGroup.TRANSPORTATION));
   }

   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
      ItemStack stack = playerIn.getHeldItem(handIn);
      RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
      if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
         return ActionResult.func_226250_c_(stack);
      } else {
         Vec3d vec3d = playerIn.getLook(1.0F);
         double size = 5;
         List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn, playerIn.getBoundingBox().expand(vec3d.scale(size)).grow(1.0D), field_219989_a);
         if (!list.isEmpty()) {
            Vec3d vec3d1 = playerIn.getEyePosition(1.0F);

            for(Entity entity : list) {
               AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow(entity.getCollisionBorderSize());
               if (axisalignedbb.contains(vec3d1)) {
                  return ActionResult.func_226250_c_(stack);
               }
            }
         }

         if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
            StemBoatEntity boatentity = new StemBoatEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
            boatentity.rotationYaw = playerIn.rotationYaw;
            if (!worldIn.func_226665_a__(boatentity, boatentity.getBoundingBox().grow(-0.1D))) {
               return ActionResult.func_226251_d_(stack);
            } else {
               if (!worldIn.isRemote) {
                  worldIn.addEntity(boatentity);
                  if (!playerIn.abilities.isCreativeMode) {
                     stack.shrink(1);
                  }
               }

               playerIn.addStat(Stats.ITEM_USED.get(this));
               return ActionResult.func_226248_a_(stack);
            }
         } else {
            return ActionResult.func_226250_c_(stack);
         }
      }
   }
}
