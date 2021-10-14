package org.golde.hypixelmod.proxy;

import org.golde.hypixelmod.SessionChanger;
import org.golde.hypixelmod.client.packets.CustomHandler;
import org.golde.hypixelmod.feature.beds.BedManager;
import org.golde.hypixelmod.feature.beds.TileEntityBed2;
import org.golde.hypixelmod.feature.beds.TileEntityBedRenderer2;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		MinecraftForge.EVENT_BUS.register(new CustomHandler());
		MinecraftForge.EVENT_BUS.register(new BedManager());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBed2.class, new TileEntityBedRenderer2());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
}
