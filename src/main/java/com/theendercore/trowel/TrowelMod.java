package com.theendercore.trowel;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = TrowelMod.MODID, name = "Trowel", version = "1.0.0")
@EventBusSubscriber
public class TrowelMod {
    public static final String MODID = "trowel";
    public static final Item TROWEL = new Trowel();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(TROWEL);
    }
}
