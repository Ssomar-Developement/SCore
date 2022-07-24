package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class TargetBlockPlaceholders extends PlaceholdersInterface implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /* placeholders of the block */
    private int blockX;
    private int blockY;
    private int blockZ;
    private UUID blockWorld;
    private String blockWorldName;
    private Material fixType;

    private String blockType = "";
    private String blockLive = "";

    public void setTargetBlockPlcHldr(Block block) {
        Location bLoc = block.getLocation();
        this.blockX = bLoc.getBlockX();
        this.blockY = bLoc.getBlockY();
        this.blockZ = bLoc.getBlockZ();
        this.blockWorld = bLoc.getWorld().getUID();
        this.reloadTargetBlockPlcHldr();
    }

    public void setTargetBlockPlcHldr(Block block, Material fixType) {
        Location bLoc = block.getLocation();
        this.blockX = bLoc.getBlockX();
        this.blockY = bLoc.getBlockY();
        this.blockZ = bLoc.getBlockZ();
        this.blockWorld = bLoc.getWorld().getUID();
        this.fixType = fixType;
        this.reloadTargetBlockPlcHldr();
    }

    public void reloadTargetBlockPlcHldr() {
        if (this.blockWorld != null) {
            World world = Bukkit.getServer().getWorld(blockWorld);
            Location loc = new Location(world, blockX, blockY, blockZ);
            Block block = loc.getBlock();

            this.blockWorldName = world.getName();

            if (this.fixType != null) {
                this.blockType = fixType.toString();
            } else this.blockType = block.getType().toString();
            this.blockLive = block.getType().toString();
        }
    }

    public String replacePlaceholder(String s) {
        String toReplace = s;
        if (blockWorld != null) {
            toReplace = toReplace.replaceAll("%target_block%", blockType);
            toReplace = toReplace.replaceAll("%target_block_lower%", blockType.toLowerCase());
            toReplace = toReplace.replaceAll("%target_block_live%", blockLive);
            toReplace = toReplace.replaceAll("%target_block_live_lower%", blockLive.toLowerCase());
            toReplace = toReplace.replaceAll("%target_block_world%", blockWorldName);
            toReplace = replaceCalculPlaceholder(toReplace, "%targte_block_x%", blockX + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%target_block_y%", blockY + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%target_block_z%", blockZ + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%target_block_x_int%", blockX + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%target_block_y_int%", blockY + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%target_block_z_int%", blockZ + "", true);
        }

        return toReplace;
    }
}
