package org.golde.hypixelmod.feature.beds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.golde.hypixelmod.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.chunk.Chunk;

public class FindBedsRunnable implements Runnable {

	private final Chunk chunk;
	
	public FindBedsRunnable(Chunk chunk){
		this.chunk = chunk;
	}
	
	@Override
	public void run() {
		
		if(chunk.isEmpty() || !chunk.isLoaded()) {
			return;
		}
		
		BedPart[] beds = findBeds();
		
		for(BedPart bed : beds) {
			
			IBlockState state = CommonProxy.BED2.getDefaultState();
			state = state.withProperty(BlockDirectional.FACING, bed.facing);
			state = state.withProperty(BlockBed2.PART, BlockBed2.EnumPartType.getFromVanillaBed(bed.part));
			
			/**
			 * ORDER MUST BE:
			 * Delete old bed client side
			 * Set new bed client side with an update value of 10
			 * Create a new Tile Entity
			 * Place said tile entity at new bed location
			 * Set tile entity color
			 * Notify neighbors of block change
			 */
			
			chunk.getWorld().setBlockToAir(bed.pos);
			
			chunk.getWorld().setBlockState(bed.pos, state, 10);
			
			TileEntityBed2 bedTE = new TileEntityBed2();
        	chunk.getWorld().setTileEntity(bed.pos, bedTE);
			
        	System.out.println("Bed: x=" + bed.pos.getX() + " y=" + bed.pos.getY() + " z=" + bed.pos.getZ() + " f=" + bed.facing + " p=" + bed.part + " c=" + bed.color);
    		
			bedTE.setColor(bed.color);
			
            chunk.getWorld().notifyNeighborsRespectDebug(bed.pos, state.getBlock());
		}

	}
	
	private BedPart[] findBeds() {
		
		List<BedPart> totalBedPartsFound = new ArrayList<BedPart>();
		
		Map<EnumDyeColor, Integer> colorsFound = new HashMap<EnumDyeColor, Integer>();
		EnumDyeColor mostUsedColor = null;
		int mostUsedColorCount = -1;
		
		List<BlockPos> bedsFound = new ArrayList<BlockPos>();
		
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				for(int y = 0; y < 256; y++) {
					
					Block block = chunk.getBlock(x, y, z);
					
					final int locX = chunk.xPosition * 16 + x;
					final int locY = y;
					final int locZ = chunk.zPosition * 16 + z;
					BlockPos pos = new BlockPos(locX, locY, locZ);
					
					IBlockState s = chunk.getBlockState(pos);
					
					//Wool
					if(block instanceof BlockColored) {
						
						EnumDyeColor color = s.getValue(BlockColored.COLOR);
						if(colorsFound.containsKey(color)) {
							int temp = colorsFound.get(color);
							temp++;
							colorsFound.put(color, temp);
						}
						else {
							colorsFound.put(color, 1);
						}
					}
					
					if(block instanceof BlockBed) {
						
						bedsFound.add(pos);
						
					}
					
				}
			}
		}
		
		for(EnumDyeColor color : colorsFound.keySet()) {
			int value = colorsFound.get(color);
			if(value > mostUsedColorCount) {
				mostUsedColor = color;
				mostUsedColorCount = value;
			}
		}
		
		for(BlockPos pos : bedsFound) {
			IBlockState s = chunk.getBlockState(pos);
			BlockBed.EnumPartType origBedPart = s.getValue(BlockBed.PART);
			EnumFacing origBedFacing = s.getValue(BlockDirectional.FACING);
			
			BedPart part = new BedPart();
			
			part.facing = s.getValue(BlockDirectional.FACING);
			part.pos = pos;
			part.part = s.getValue(BlockBed.PART);
			part.color = mostUsedColor;
			
			totalBedPartsFound.add(part);
		}
		
		return totalBedPartsFound.toArray(new BedPart[0]);
		
	}
	
	static class BedPart {
		BlockPos pos;
		BlockBed.EnumPartType part;
		EnumFacing facing;
		EnumDyeColor color;
	}
	
}
