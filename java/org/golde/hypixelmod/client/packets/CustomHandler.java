package org.golde.hypixelmod.client.packets;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@ChannelHandler.Sharable
public class CustomHandler extends SimpleChannelInboundHandler<Packet<INetHandlerPlayClient>>{

	public CustomHandler() {
		super(false);
	}

	@SubscribeEvent
	public void connect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		ChannelPipeline pipeline = event.manager.channel().pipeline();
		pipeline.addBefore("packet_handler", this.getClass().getName(), this);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet<INetHandlerPlayClient> msg) throws Exception {

//		if(msg instanceof S35PacketUpdateTileEntity) {
//			S35PacketUpdateTileEntity packet = (S35PacketUpdateTileEntity)msg;
//			
//			System.out.println("Update: " + packet.getTileEntityType() + " - " + packet.getNbtCompound().toString());
//		}

		ctx.fireChannelRead(msg);
	}

}
