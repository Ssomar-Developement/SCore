package com.ssomar.score.utils.placeholders;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class TargetBlockPlaceholders extends PlaceholdersInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* placeholders of the block */
	private Block targetBlock;
	
	private String targetBlockType = "";
	private String targetBlockWorld = "";
	private String targetBlockX= "";
	private String targetBlockY= "";
	private String targetBlockZ= "";
	private String targetBlockXInt= "";
	private String targetBlockYInt= "";
	private String targetBlockZInt= "";
	
	public void setTargetBlockPlcHldr(Block block) {
		this.targetBlock = block;
		this.reloadTargetBlockPlcHldr();
	}

	public void reloadTargetBlockPlcHldr() {
		if(this.targetBlock != null ) {
			this.targetBlockType = targetBlock.getType().toString();
			Location bLoc = targetBlock.getLocation();
			this.targetBlockWorld = bLoc.getWorld().getName();
			this.targetBlockX = bLoc.getX()+"";
			this.targetBlockY = bLoc.getY()+"";
			this.targetBlockZ = bLoc.getZ()+"";
			this.targetBlockXInt = bLoc.getBlockX()+"";
			this.targetBlockYInt = bLoc.getBlockY()+"";
			this.targetBlockZInt = bLoc.getBlockZ()+"";
		}
	}
	
	public String replacePlaceholder(String s) {
		String toReplace = s;
		if(targetBlock != null) {
			toReplace = toReplace.replaceAll("%target_block%", targetBlockType);
			toReplace = toReplace.replaceAll("%target_block_lower%", targetBlockType.toLowerCase());
			toReplace = toReplace.replaceAll("%target_block_world%", targetBlockWorld);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_x%", targetBlockX, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_y%", targetBlockY, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_z%", targetBlockZ, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_x_int%", targetBlockXInt, true);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_y_int%", targetBlockYInt, true);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_z_int%", targetBlockZInt, true);
		}
		
		return toReplace;
	}
}
