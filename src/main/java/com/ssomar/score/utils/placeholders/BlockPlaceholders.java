package com.ssomar.score.utils.placeholders;

import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.utils.ToolsListMaterial;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    @Getter
    private Map<String, String> placeholders;

    public BlockPlaceholders() {
        this.placeholders = new HashMap<>();
    }

    public void setBlockPlcHldr(Block block) {
        setBlockPlcHldr(block, null);
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
        if(fixType != null) this.fixType = fixType;
        this.reloadBlockPlcHldr();
    }

    public void reloadBlockPlcHldr() {
        if (this.blockWorld != null) {
            Optional<World> worldOpt = AllWorldManager.getWorld(this.blockWorld);
            if(!worldOpt.isPresent()) return;
            World world = worldOpt.get();
            Location loc = new Location(world, blockX, blockY, blockZ);
            Block block = loc.getBlock();

            this.blockWorldName = world.getName();
            Material type = block.getType();

            String blockType = type.toString();
            if (this.fixType != null)
                blockType = fixType.toString();

            placeholders.put("%block%", blockType);
            placeholders.put("%block_lower%", blockType.toLowerCase());
            placeholders.put("%block_item_material%", ToolsListMaterial.getInstance().getRealMaterialOfBlock(Material.valueOf(blockType)).toString());
            placeholders.put("%block_item_material_lower%", ToolsListMaterial.getInstance().getRealMaterialOfBlock(Material.valueOf(blockType)).toString().toLowerCase());
            placeholders.put("%block_live%", type.toString());
            placeholders.put("%block_live_lower%", type.toString().toLowerCase());
            placeholders.put("%block_world%", blockWorldName);
            placeholders.put("%block_world_lower%", blockWorldName.toLowerCase());
            placeholders.put("%block_dimension%", blockDimension);
            placeholders.put("%block_data%", block.getBlockData().getAsString());

            if(block.getState() instanceof CreatureSpawner){
                CreatureSpawner spawner = (CreatureSpawner) block.getState();
                placeholders.put("%block_spawnertype%", spawner.getSpawnedType().toString());
            }else{
                placeholders.put("%block_spawnertype%", "null");
            }
        }
    }

    public String replacePlaceholder(String s) {
        String toReplace = s;
        if (blockWorld != null) {
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
