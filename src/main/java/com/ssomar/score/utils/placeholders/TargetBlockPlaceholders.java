package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class TargetBlockPlaceholders extends PlaceholdersInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* placeholders of the block */
	private int targetBlockX;
	private int targetBlockY;
	private int targetBlockZ;
	private UUID targetBlockWorld;
	private String targetBlockWorldName;
	
	private String targetBlockType = "";
	
	public void setTargetBlockPlcHldr(Block block) {
		Location bLoc = block.getLocation();
		this.targetBlockX = bLoc.getBlockX();
		this.targetBlockY = bLoc.getBlockY();
		this.targetBlockZ = bLoc.getBlockZ();
		this.targetBlockWorld = bLoc.getWorld().getUID();
		this.reloadTargetBlockPlcHldr();
	}

	public void reloadTargetBlockPlcHldr() {
		if(this.targetBlockWorld != null ) {
			
			World world = Bukkit.getServer().getWorld(targetBlockWorld);
			Location loc = new Location(world, targetBlockX, targetBlockY, targetBlockZ);
			Block targetBlock = loc.getBlock();
			targetBlockWorldName = world.getName();
			
			this.targetBlockType = targetBlock.getType().toString();
		}
	}
	
	public String replacePlaceholder(String s) {
		String toReplace = s;
		if(targetBlockWorld != null) {
			toReplace = toReplace.replaceAll("%target_block%", targetBlockType);
			toReplace = toReplace.replaceAll("%target_block_lower%", targetBlockType.toLowerCase());
			toReplace = toReplace.replaceAll("%target_block_world%", targetBlockWorldName);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_x%", targetBlockX+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_y%", targetBlockY+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_z%", targetBlockZ+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_x_int%", targetBlockX+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_y_int%", targetBlockY+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_block_z_int%", targetBlockZ+"", true);
		}
		
		return toReplace;
	}
}
