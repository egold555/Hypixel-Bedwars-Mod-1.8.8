package org.golde.hypixelmod.feature.beds;

import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class BedManager {

	@SubscribeEvent
	public void chunkLoad(ChunkEvent.Load event) {
		//
		if(event.getChunk().getWorld().isRemote) {
			Thread t = new Thread(new FindBedsRunnable(event.getChunk()), "Bed Finder 9000");
			t.start();
			//System.out.println("Starting remote thread");
		}
		
	}

}
