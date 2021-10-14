package org.golde.hypixelmod;

import org.golde.hypixelmod.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = HypixelMod.MOD_ID, version = HypixelMod.VERSION, name = HypixelMod.MOD_NAME)
public class HypixelMod
{
	public static final String MOD_ID = "ericshypixelmod";
	public static final String MOD_NAME = MOD_ID;
	public static final String VERSION = "1.0";
	public static final String PREFIX_MOD = MOD_ID + ":";

	// Proxy Constants
	public static final String PROXY_COMMON = "org.golde.hypixelmod.proxy.CommonProxy";
	public static final String PROXY_CLIENT = "org.golde.hypixelmod.proxy.ClientProxy";
    
    @Instance(HypixelMod.MOD_ID)
	public static HypixelMod instance;

	@SidedProxy(serverSide = HypixelMod.PROXY_COMMON, clientSide = HypixelMod.PROXY_CLIENT)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}
}

