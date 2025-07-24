package com.theendercore.trowel;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = TrowelMod.MODID, value = Side.CLIENT)
public final class ClientProxy {

    @SubscribeEvent
    public static void registerModels(net.minecraftforge.client.event.ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(
                TrowelMod.TROWEL, 0,
                new ModelResourceLocation(TrowelMod.TROWEL.getRegistryName(), "inventory")
        );
    }
}
