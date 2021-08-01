package com.ssomar.score.utils.placeholders;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class BlockPlaceholders extends PlaceholdersInterface{

	/* placeholders of the block */
	private Block block;
	
	private String blockType = "";
	private String blockWorld = "";
	private String blockX= "";
	private String blockY= "";
	private String blockZ= "";
	private String blockXInt= "";
	private String blockYInt= "";
	private String blockZInt= "";
	
	public void setBlockPlcHldr(Block block) {
		this.block = block;
		this.reloadBlockPlcHldr();
	}

	public void reloadBlockPlcHldr() {
		if(this.block != null ) {
			this.blockType = block.getType().toString();
			Location bLoc = block.getLocation();
			this.blockWorld = bLoc.getWorld().getName();
			this.blockX = bLoc.getX()+"";
			this.blockY = bLoc.getY()+"";
			this.blockZ = bLoc.getZ()+"";
			this.blockXInt = bLoc.getBlockX()+"";
			this.blockYInt = bLoc.getBlockY()+"";
			this.blockZInt = bLoc.getBlockZ()+"";
		}
	}
	
	public String replacePlaceholder(String s) {
		if(block != null) {
			s = s.replaceAll("%block%", blockType);
			s = s.replaceAll("%block_lower%", blockType.toLowerCase());
			s = s.replaceAll("%block_world%", blockWorld);
			s = replaceCalculPlaceholder(s, "%block_x%", blockX, false);
			s = replaceCalculPlaceholder(s, "%block_y%", blockY, false);
			s = replaceCalculPlaceholder(s, "%block_z%", blockZ, false);
			s = replaceCalculPlaceholder(s, "%block_x_int%", blockXInt, true);
			s = replaceCalculPlaceholder(s, "%block_y_int%", blockYInt, true);
			s = replaceCalculPlaceholder(s, "%block_z_int%", blockZInt, true);
		}
		
		return s;
	}
}
