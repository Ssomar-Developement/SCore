package com.ssomar.score.utils;

import com.ssomar.score.SCore;
import org.bukkit.Material;

import javax.annotation.Nullable;
import java.util.*;

public class ToolsListMaterial {

    private static ToolsListMaterial instance;
    private static Map<Material, Material> blockAndItemMaterial;
    private List<Material> plantWithGrowth;

    private List<Material> plantWithGrowthOnlyFarmland;

    private List<Material> plantWithGrowthOnlySoulSand;

    public ToolsListMaterial() {
        plantWithGrowth = new ArrayList<>();
        plantWithGrowthOnlyFarmland = new ArrayList<>();
        plantWithGrowthOnlySoulSand = new ArrayList<>();

        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("WHEAT", "CROPS")));
        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("CARROTS", "CARROT")));
        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("BEETROOTS", "BEETROOT_BLOCK")));
        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("POTATOES", "POTATO")));
        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("NETHER_WART", "NETHER_WARTS")));
        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("COCOA")));

        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("KELP")));
        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("SWEET_BERRY_BUSH")));
        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("MELON_STEM")));
        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("PUMPKIN_STEM")));
        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("CAVE_VINES")));
        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("CAVE_VINES_PLANT")));
        addWithoutProblem(plantWithGrowth, FixedMaterial.getMaterial(Arrays.asList("GLOW_BERRIES")));


        addWithoutProblem(plantWithGrowthOnlyFarmland, FixedMaterial.getMaterial(Arrays.asList("WHEAT", "CROPS")));
        addWithoutProblem(plantWithGrowthOnlyFarmland, FixedMaterial.getMaterial(Arrays.asList("CARROTS", "CARROT")));
        addWithoutProblem(plantWithGrowthOnlyFarmland, FixedMaterial.getMaterial(Arrays.asList("BEETROOTS", "BEETROOT_BLOCK")));
        addWithoutProblem(plantWithGrowthOnlyFarmland, FixedMaterial.getMaterial(Arrays.asList("POTATOES", "POTATO")));
        addWithoutProblem(plantWithGrowthOnlyFarmland, FixedMaterial.getMaterial(Arrays.asList("SWEET_BERRY_BUSH")));
        addWithoutProblem(plantWithGrowthOnlyFarmland, FixedMaterial.getMaterial(Arrays.asList("MELON_STEM")));
        addWithoutProblem(plantWithGrowthOnlyFarmland, FixedMaterial.getMaterial(Arrays.asList("PUMPKIN_STEM")));

        addWithoutProblem(plantWithGrowthOnlySoulSand, FixedMaterial.getMaterial(Arrays.asList("NETHER_WART", "NETHER_WARTS")));

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

    public void addWithoutProblem(List<Material> list, Material material) {
        if (material == Material.BARRIER) return;

        list.add(material);
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

    public List<Material> getPlantWithGrowthOnlyFarmland() {
        return plantWithGrowthOnlyFarmland;
    }

    public List<Material> getPlantWithGrowthOnlySoulSand() {
        return plantWithGrowthOnlySoulSand;
    }

    public void setPlantWithGrowth(List<Material> plantWithGrowth) {
        this.plantWithGrowth = plantWithGrowth;
    }

}
