package com.theendercore.trowel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trowel extends Item {

    public Trowel() {
        super(new Item.Settings().maxCount(1).group(ItemGroup.TOOLS));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) return ActionResult.PASS;

        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResult.PASS;

        PlayerInventory inv = player.inventory;
        List<Pair<Integer, ItemStack>> placeable = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            ItemStack stack = inv.getInvStack(i);
            if (isPlaceable(stack)) {
                placeable.add(new Pair<>(i, stack));
            }
        }

        if (placeable.isEmpty()) return ActionResult.PASS;

        return place(placeable, inv, player, context, inv.selectedSlot);
    }

    private ActionResult place(List<Pair<Integer, ItemStack>> placeable, PlayerInventory inv,
                               PlayerEntity player, ItemUsageContext ctx, int originalSlot) {
        if (placeable.isEmpty()) {
            inv.selectedSlot = originalSlot;
            return ActionResult.FAIL;
        }

        Collections.shuffle(placeable);

        for (Pair<Integer, ItemStack> pair : placeable) {
            int slot = pair.left;
            ItemStack stack = pair.right;

            inv.selectedSlot = slot;
            ItemPlacementContext placementContext = new ItemPlacementContext(ctx);

            Item item = stack.getItem();
            if (item instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) item;
                if (canPlace(placementContext, blockItem)) {
                    placeSound(ctx);
                    inv.selectedSlot = originalSlot;
                    return ActionResult.SUCCESS;
                }
            }
        }

        inv.selectedSlot = originalSlot;
        return ActionResult.FAIL;
    }

    private void placeSound(ItemUsageContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        BlockSoundGroup sound = block.getSoundGroup(state);

        world.playSound(
                null,
                pos,
                sound.getPlaceSound(),
                SoundCategory.BLOCKS,
                (sound.getVolume() + 1.0f) / 2.0f,
                sound.getPitch() * 0.8f
        );
    }

    private boolean isPlaceable(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof BlockItem && !(item instanceof AirBlockItem);
    }

    private boolean canPlace(ItemPlacementContext ctx, BlockItem blockItem) {
        BlockState state = blockItem.getBlock().getPlacementState(ctx);
        if (state == null) return false;

        EntityContext shapeContext = ctx.getPlayer() == null
                ? EntityContext.absent()
                : EntityContext.of(ctx.getPlayer());

        return ctx.canPlace()
                && state.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())
                && ctx.getWorld().canPlace(state, ctx.getBlockPos(), shapeContext)
                && blockItem.place(ctx) == ActionResult.SUCCESS;
    }

    // Manual Pair class (Java 8 compatible)
    private static class Pair<L, R> {
        public final L left;
        public final R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }
    }
}
