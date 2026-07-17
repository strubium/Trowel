package com.theendercore.trowel;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trowel extends Item {

    public Trowel() {
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.TOOLS);
        setRegistryName("trowel");
        setTranslationKey("trowel");
        setMaxDamage(255);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return EnumActionResult.PASS;

        ItemStack held = player.getHeldItem(hand);
        List<Pair<Integer, ItemStack>> placeable = new ArrayList<>();

        for (int i = 0; i <= 8; i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (isPlaceable(stack)) {
                placeable.add(new Pair<>(i, stack));
            }
        }

        if (placeable.isEmpty()) return EnumActionResult.PASS;

        player.getHeldItem(hand).damageItem(1, player);
        return tryPlace(world, player, pos, facing, hand, placeable, player.inventory.currentItem);
    }

    private EnumActionResult tryPlace(World world, EntityPlayer player, BlockPos pos, EnumFacing facing, EnumHand hand,
                                      List<Pair<Integer, ItemStack>> options, int originalSlot) {
        Collections.shuffle(options);

        for (Pair<Integer, ItemStack> pair : options) {
            int slot = pair.left;
            ItemStack stack = pair.right;

            player.inventory.currentItem = slot;

            Item item = stack.getItem();
            if (item instanceof ItemBlock) {
                ItemBlock blockItem = (ItemBlock) item;
                EnumActionResult result = blockItem.onItemUse(player, world, pos, hand, facing, 0.5F, 0.5F, 0.5F);
                if (result == EnumActionResult.SUCCESS) {
                    playPlaceSound(world, pos);
                    player.inventory.currentItem = originalSlot;
                    return EnumActionResult.SUCCESS;
                }
            }
        }

        player.inventory.currentItem = originalSlot;
        return EnumActionResult.FAIL;
    }

    private void playPlaceSound(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        SoundType sound = state.getBlock().getSoundType(state, world, pos, null);
        world.playSound(null, pos, sound.getPlaceSound(), SoundCategory.BLOCKS,
                (sound.getVolume() + 1.0f) / 2.0f, sound.getPitch() * 0.8f);
    }

    private boolean isPlaceable(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof ItemBlock && stack.getItem() != Item.getItemFromBlock(Blocks.AIR);
    }

    // Manual Pair class
    private static class Pair<L, R> {
        public final L left;
        public final R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }
    }
}
