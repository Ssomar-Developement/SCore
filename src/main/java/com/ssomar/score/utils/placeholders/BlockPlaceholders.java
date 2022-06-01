package com.ssomar.score.utils.placeholders;

import com.ssomar.score.utils.ToolsListMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.Serializable;
import java.util.UUID;

public class BlockPlaceholders extends PlaceholdersInterface implements Serializable {

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
    private String blockDimension;
    private Material fixType;

    private String blockType = "";
    private String blockLive = "";

    public void setBlockPlcHldr(Block block) {
        Location bLoc = block.getLocation();
        this.blockX = bLoc.getBlockX();
        this.blockY = bLoc.getBlockY();
        this.blockZ = bLoc.getBlockZ();
        this.blockWorld = bLoc.getWorld().getUID();
        switch (block.getWorld().getEnvironment()) {
            case NETHER:
                blockDimension = "nether";
                break;
            case CUSTOM:
                blockDimension = "custom";
                break;
            case NORMAL:
                blockDimension = "normal";
                break;
            case THE_END:
                blockDimension = "end";
                break;
        }
        this.reloadBlockPlcHldr();
    }

    public void setBlockPlcHldr(Block block, Material fixType) {
        Location bLoc = block.getLocation();
        this.blockX = bLoc.getBlockX();
        this.blockY = bLoc.getBlockY();
        this.blockZ = bLoc.getBlockZ();
        this.blockWorld = bLoc.getWorld().getUID();
        switch (block.getWorld().getEnvironment()) {
            case NETHER:
                blockDimension = "nether";
                break;
            case CUSTOM:
                blockDimension = "custom";
                break;
            case NORMAL:
                blockDimension = "normal";
                break;
            case THE_END:
                blockDimension = "end";
                break;
        }
        this.fixType = fixType;
        this.reloadBlockPlcHldr();
    }

    public void reloadBlockPlcHldr() {
        if (this.blockWorld != null) {
            World world = Bukkit.getServer().getWorld(blockWorld);
            Location loc = new Location(world, blockX, blockY, blockZ);
            Block block = loc.getBlock();

            this.blockWorldName = world.getName();
			Material type = block.getType();

            if (this.fixType != null) {
                this.blockType = fixType.toString();
            }
            else this.blockType = type.toString();

            this.blockLive = type.toString();
        }
    }

    public String replacePlaceholder(String s) {
        String toReplace = s;
        if (blockWorld != null) {
            toReplace = toReplace.replaceAll("%block%", blockType);
            toReplace = toReplace.replaceAll("%block_lower%", blockType.toLowerCase());
            toReplace = toReplace.replaceAll("%block_item_material%", ToolsListMaterial.getRealMaterialOfBlock(Material.valueOf(blockType)).toString());
            toReplace = toReplace.replaceAll("%block_item_material_lower%", ToolsListMaterial.getRealMaterialOfBlock(Material.valueOf(blockType)).toString().toLowerCase());
            toReplace = toReplace.replaceAll("%block_live%", blockLive);
            toReplace = toReplace.replaceAll("%block_live_lower%", blockLive.toLowerCase());
            toReplace = toReplace.replaceAll("%block_world%", blockWorldName);
            toReplace = toReplace.replaceAll("%block_world_lower%", blockWorldName.toLowerCase());
            toReplace = toReplace.replaceAll("%block_dimension%", blockDimension);
            toReplace = replaceCalculPlaceholder(toReplace, "%block_x%", blockX + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%block_y%", blockY + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%block_z%", blockZ + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%block_x_int%", blockX + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%block_y_int%", blockY + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%block_z_int%", blockZ + "", true);
        }

        return toReplace;
    }
}
