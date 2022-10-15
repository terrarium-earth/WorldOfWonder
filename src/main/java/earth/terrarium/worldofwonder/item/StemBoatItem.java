package earth.terrarium.worldofwonder.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import earth.terrarium.worldofwonder.entity.StemBoatEntity;

import java.util.List;
import java.util.function.Predicate;

public class StemBoatItem extends Item {
   private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

   public StemBoatItem() {
      super(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TRANSPORTATION));
   }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.getItemInHand(handIn);
      BlockHitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.ANY);
      if (raytraceresult.getType() == HitResult.Type.MISS) {
         return InteractionResultHolder.pass(stack);
      } else {
          Vec3 vec3d = playerIn.getViewVector(1.0F);
          double size = 5;
          List<Entity> list = worldIn.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vec3d.scale(size)).inflate(1.0D), ENTITY_PREDICATE);
          if (!list.isEmpty()) {
              Vec3 vec3d1 = playerIn.getEyePosition(1.0F);

              for (Entity entity : list) {
                  AABB axisalignedbb = entity.getBoundingBox().inflate(entity.getPickRadius());
                  if (axisalignedbb.contains(vec3d1)) {
                      return InteractionResultHolder.pass(stack);
                  }
              }
          }

          StemBoatEntity boatentity = new StemBoatEntity(worldIn, raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z);
          boatentity.setYRot(playerIn.getYRot());
          if (!worldIn.noCollision(boatentity, boatentity.getBoundingBox().deflate(0.1D))) {
              return InteractionResultHolder.fail(stack);
          } else {
              if (!worldIn.isClientSide) {
                  worldIn.addFreshEntity(boatentity);
                  if (!playerIn.getAbilities().instabuild) {
                      stack.shrink(1);
                  }
              }

              playerIn.awardStat(Stats.ITEM_USED.get(this));
              return InteractionResultHolder.success(stack);
          }
      }
   }
}

