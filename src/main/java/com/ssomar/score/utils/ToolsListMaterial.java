package com.ssomar.score.utils;

import com.ssomar.score.SCore;
import org.bukkit.Material;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolsListMaterial {

    private static ToolsListMaterial instance;
    private static Map<Material, Material> blockAndItemMaterial;
    private List<Material> plantWithGrowth;

    public ToolsListMaterial() {
        plantWithGrowth = new ArrayList<>();
        if (SCore.is1v12Less()) {
            plantWithGrowth.add(Material.valueOf("CROPS"));
            plantWithGrowth.add(Material.valueOf("NETHER_WARTS"));
            plantWithGrowth.add(Material.valueOf("POTATO"));
            plantWithGrowth.add(Material.valueOf("CARROT"));
            if (!SCore.is1v11Less()) plantWithGrowth.add(Material.valueOf("BEETROOT_BLOCK"));
        } else {
            plantWithGrowth.add(Material.WHEAT);
            plantWithGrowth.add(Material.CARROTS);
            plantWithGrowth.add(Material.BEETROOTS);
            plantWithGrowth.add(Material.POTATOES);
            plantWithGrowth.add(Material.NETHER_WART);
            plantWithGrowth.add(Material.COCOA);
        }

        blockAndItemMaterial = new HashMap<>();
        if (SCore.is1v12Less()) {
            blockAndItemMaterial.put(Material.valueOf("CROPS"), Material.valueOf("SEEDS"));
            blockAndItemMaterial.put(Material.valueOf("POTATO"), Material.valueOf("POTATO_ITEM"));
            if (!SCore.is1v11Less())
                blockAndItemMaterial.put(Material.valueOf("BEETROOT_BLOCK"), Material.valueOf("BEETROOT_SEEDS"));
            blockAndItemMaterial.put(Material.valueOf("CARROT"), Material.valueOf("CARROT_ITEM"));
        } else {
            blockAndItemMaterial.put(Material.WHEAT, Material.WHEAT_SEEDS);
            blockAndItemMaterial.put(Material.CARROTS, Material.CARROT);
            blockAndItemMaterial.put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
            blockAndItemMaterial.put(Material.POTATOES, Material.POTATO);
            blockAndItemMaterial.put(Material.COCOA, Material.COCOA_BEANS);
        }
        blockAndItemMaterial.put(Material.TRIPWIRE, Material.STRING);
        blockAndItemMaterial.put(Material.REDSTONE_WIRE, Material.REDSTONE);

    }

    public static ToolsListMaterial getInstance() {
        if (instance == null) return new ToolsListMaterial();
        return instance;
    }

    @Nullable
    public Material getRealMaterialOfBlock(Material material) {
        if (blockAndItemMaterial.containsKey(material)) {
            return blockAndItemMaterial.get(material);
        } else return material;
    }

    @Nullable
    public Material getBlockMaterialOfItem(Material material) {
        for (Material key : blockAndItemMaterial.keySet()) {
            if (blockAndItemMaterial.get(key) == material) {
                return key;
            }
        }
        return material;
    }

    public List<Material> getPlantWithGrowth() {
        return plantWithGrowth;
    }

    public void setPlantWithGrowth(List<Material> plantWithGrowth) {
        this.plantWithGrowth = plantWithGrowth;
    }

}
