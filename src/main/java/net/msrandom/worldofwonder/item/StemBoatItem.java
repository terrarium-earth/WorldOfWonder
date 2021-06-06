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
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.msrandom.worldofwonder.entity.StemBoatEntity;

import java.util.List;
import java.util.function.Predicate;

public class StemBoatItem extends Item {
   private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);

   public StemBoatItem() {
      super(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_TRANSPORTATION));
   }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
      ItemStack stack = playerIn.getItemInHand(handIn);
      BlockRayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
      if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
         return ActionResult.pass(stack);
      } else {
          Vector3d vec3d = playerIn.getViewVector(1.0F);
          double size = 5;
          List<Entity> list = worldIn.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vec3d.scale(size)).inflate(1.0D), ENTITY_PREDICATE);
          if (!list.isEmpty()) {
              Vector3d vec3d1 = playerIn.getEyePosition(1.0F);

              for (Entity entity : list) {
                  AxisAlignedBB axisalignedbb = entity.getBoundingBox().inflate(entity.getPickRadius());
                  if (axisalignedbb.contains(vec3d1)) {
                      return ActionResult.pass(stack);
                  }
              }
          }

          StemBoatEntity boatentity = new StemBoatEntity(worldIn, raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z);
          boatentity.yRot = playerIn.yRot;
          if (!worldIn.noCollision(boatentity, boatentity.getBoundingBox().deflate(0.1D))) {
              return ActionResult.fail(stack);
          } else {
              if (!worldIn.isClientSide) {
                  worldIn.addFreshEntity(boatentity);
                  if (!playerIn.abilities.instabuild) {
                      stack.shrink(1);
                  }
              }

              playerIn.awardStat(Stats.ITEM_USED.get(this));
              return ActionResult.success(stack);
          }
      }
   }
}

