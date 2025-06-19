package com.theendercore.trowel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrowelMod implements ModInitializer {
    public static final String MODID = "trowel";
    public static final Logger LOG = LoggerFactory.getLogger(MODID);
    public static final Item TROWEL = new Trowel();

    @Override
    public void onInitialize() {
        LOG.info("Seizing the means of block placement!");

        Registry.register(Registries.ITEM, new Identifier(MODID, MODID), TROWEL);

        RegistryKey<ItemGroup> TOOLS_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("minecraft", "tools_and_utilities"));


        ItemGroupEvents.modifyEntriesEvent(TOOLS_KEY)
                .register(entries -> entries.addAfter(Items.SHEARS, TROWEL));
    }
}

