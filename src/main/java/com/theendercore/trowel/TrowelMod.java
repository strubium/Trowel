package com.theendercore.trowel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrowelMod implements ModInitializer {
    public static final String MODID = "trowel";
    public static final Logger LOG = LoggerFactory.getLogger(MODID);
    public static final Item TROWEL = new Trowel();

    public static final ItemGroup TROWEL_GROUP = FabricItemGroupBuilder.create(
                    new Identifier(MODID, "trowel_group"))
            .icon(() -> new ItemStack(TROWEL))
            .appendItems(stacks -> stacks.add(new ItemStack(TROWEL)))
            .build();

    @Override
    public void onInitialize() {
        LOG.info("Seizing the means of block placement!");
        Registry.register(Registry.ITEM, new Identifier(MODID, MODID), TROWEL);
    }
}
