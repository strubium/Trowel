package com.theendercore.trowel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TrowelMod implements ModInitializer {
    public static final String MODID = "trowel";
    public static final Item TROWEL = new Trowel();

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(MODID, MODID), TROWEL);
    }
}
