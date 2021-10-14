package org.golde.hypixelmod.proxy;

import org.golde.hypixelmod.feature.beds.BlockBed2;
import org.golde.hypixelmod.feature.beds.TileEntityBed2;
import org.golde.hypixelmod.item.ItemBed2;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public static BlockBed2 BED2;
	
	public void preInit(FMLPreInitializationEvent event) {

	}

	public void init(FMLInitializationEvent event) {
		GameRegistry.registerItem(new ItemBed2(), "item_bed_2");
		GameRegistry.registerBlock(BED2 = new BlockBed2(), "block_bed_2");
		GameRegistry.registerTileEntity(TileEntityBed2.class, "tile_entity_block_bed_2");
	}

	public void postInit(FMLPostInitializationEvent event) {
		
	}

	public void serverStarting(FMLServerStartingEvent event) {
		
	}
	
}
