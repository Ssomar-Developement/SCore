package com.ssomar.score.utils.placeholders;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockPlaceholders extends PlaceholdersInterface{

	/* placeholders of the block */
	private Block block;
	private Material fixType;

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
	
	public void setBlockPlcHldr(Block block, Material fixType) {
		this.block = block;
		this.fixType = fixType;
		this.reloadBlockPlcHldr();
	}

	public void reloadBlockPlcHldr() {
		if(this.block != null ) {
			if(this.fixType != null) {
				this.blockType = fixType.toString();
			}
			else this.blockType = block.getType().toString();
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
		String toReplace = s;
		if(block != null) {
			toReplace = toReplace.replaceAll("%block%", blockType);
			toReplace = toReplace.replaceAll("%block_lower%", blockType.toLowerCase());
			toReplace = toReplace.replaceAll("%block_world%", blockWorld);
			toReplace = replaceCalculPlaceholder(toReplace, "%block_x%", blockX, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%block_y%", blockY, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%block_z%", blockZ, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%block_x_int%", blockXInt, true);
			toReplace = replaceCalculPlaceholder(toReplace, "%block_y_int%", blockYInt, true);
			toReplace = replaceCalculPlaceholder(toReplace, "%block_z_int%", blockZInt, true);
		}

		return toReplace;
	}
}
