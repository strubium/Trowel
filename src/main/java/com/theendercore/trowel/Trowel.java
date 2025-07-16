package com.theendercore.trowel;

import net.minecraft.block.ShapeContext;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trowel extends Item {

    public Trowel() {
        super(new Settings().maxCount(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient()) return ActionResult.PASS;

        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResult.PASS;

        PlayerInventory inv = player.getInventory();
        if (inv == null) return ActionResult.PASS;

        List<Pair<Integer, ItemStack>> placeable = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            ItemStack stack = inv.getStack(i);
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

        Collections.shuffle(placeable); // mimic Kotlin .random()

        for (Pair<Integer, ItemStack> pair : placeable) {
            int slot = pair.getLeft();
            ItemStack stack = pair.getRight();

            inv.selectedSlot = slot;

            ItemPlacementContext placementContext = newPlacementContext(player, stack, ctx);
            Item item = stack.getItem();
            if (item instanceof BlockItem blockItem && canPlace(placementContext, blockItem)) {
                placeSound(ctx);
                inv.selectedSlot = originalSlot;
                return ActionResult.SUCCESS;
            }
        }

        inv.selectedSlot = originalSlot;
        return ActionResult.FAIL;
    }

    private void placeSound(ItemUsageContext ctx) {
        BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos());
        ctx.getWorld().playSound(
                null,
                ctx.getBlockPos(),
                state.getSoundGroup().getPlaceSound(),
                SoundCategory.BLOCKS,
                (state.getSoundGroup().getVolume() + 1.0f) / 2.0f,
                state.getSoundGroup().getPitch() * 0.8f
        );
    }

    private ItemPlacementContext newPlacementContext(PlayerEntity player, ItemStack stack, ItemUsageContext ctx) {
        return new ItemPlacementContext(player, ctx.getHand(), stack,
                new BlockHitResult(ctx.getHitPos(), ctx.getSide(), ctx.getBlockPos(), ctx.hitsInsideBlock()));
    }

    private boolean isPlaceable(ItemStack stack) {
        return !(stack.getItem() instanceof AirBlockItem) && stack.getItem() instanceof BlockItem;
    }

    private boolean canPlace(ItemPlacementContext ctx, BlockItem blockItem) {
        BlockState state = blockItem.getBlock().getPlacementState(blockItem.getPlacementContext(ctx));
        if (state == null) return false;

        ShapeContext shapeContext = ctx.getPlayer() == null
                ? ShapeContext.absent()
                : ShapeContext.of(ctx.getPlayer());

        return ctx.canPlace()
                && state.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())
                && ctx.getWorld().canPlace(state, ctx.getBlockPos(), shapeContext)
                && blockItem.useOnBlock(ctx).shouldIncrementStat();
    }

    // Simple Pair class since Java doesn't have one by default
    private record Pair<L, R>(L left, R right) {
        public L getLeft() { return left; }
        public R getRight() { return right; }
    }
}

