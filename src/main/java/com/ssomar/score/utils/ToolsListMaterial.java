package com.ssomar.score.utils;

import com.ssomar.score.SCore;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import javax.annotation.Nullable;
import java.util.*;

@Getter @Setter
public class ToolsListMaterial {

    private static ToolsListMaterial instance;
    private static Map<Material, Material> blockAndItemMaterial;
    private List<Material> plantWithGrowth;

    private List<Material> plantWithGrowthOnlyFarmland;

    private List<Material> plantWithGrowthOnlySoulSand;

    private List<Material> plantWithGrowthOnlyJungleWood;

    private List<Material> oneUsageMaterial;

    /**
     * Contains {@link Material#JUNGLE_LOG} and {@link Material#JUNGLE_WOOD}
     */
    private List<Material> validJungleBlockMaterials;

    /**
     * Used to store Material enums while considering the game version of the server.<br/>
     * Sample Scenario: You want to be able to place cocoa on all valid jungle log blocks but if you boot
     * up your server at 1.12, only some minecraft enums are valid
     */
    public ToolsListMaterial() {
        plantWithGrowth = new ArrayList<>();
        plantWithGrowthOnlyFarmland = new ArrayList<>();
        plantWithGrowthOnlySoulSand = new ArrayList<>();
        plantWithGrowthOnlyJungleWood = new ArrayList<>();
        oneUsageMaterial = new ArrayList<>();
        validJungleBlockMaterials = new ArrayList<>();

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
        addWithoutProblem(plantWithGrowthOnlyFarmland, FixedMaterial.getMaterial(Arrays.asList("TORCHFLOWER_CROP")));

        addWithoutProblem(plantWithGrowthOnlySoulSand, FixedMaterial.getMaterial(Arrays.asList("NETHER_WART", "NETHER_WARTS")));

        addWithoutProblem(plantWithGrowthOnlyJungleWood, FixedMaterial.getMaterial(Arrays.asList("COCOA")));

        addWithoutProblem(validJungleBlockMaterials, FixedMaterial.getMaterial(Collections.singletonList("JUNGLE_WOOD")));
        addWithoutProblem(validJungleBlockMaterials, FixedMaterial.getMaterial(Collections.singletonList("JUNGLE_LOG")));
        addWithoutProblem(validJungleBlockMaterials, FixedMaterial.getMaterial(Collections.singletonList("STRIPPED_JUNGLE_WOOD")));
        addWithoutProblem(validJungleBlockMaterials, FixedMaterial.getMaterial(Collections.singletonList("STRIPPED_JUNGLE_LOG")));

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
        blockAndItemMaterial.put(FixedMaterial.getMaterial(Arrays.asList("MELON_STEM")), FixedMaterial.getMaterial(Arrays.asList("MELON_SEEDS")));
        blockAndItemMaterial.put(FixedMaterial.getMaterial(Arrays.asList("MELON")), FixedMaterial.getMaterial(Arrays.asList("MELON_SEEDS")));
        blockAndItemMaterial.put(FixedMaterial.getMaterial(Arrays.asList("PUMPKIN_STEM")), FixedMaterial.getMaterial(Arrays.asList("PUMPKIN_SEEDS")));
        blockAndItemMaterial.put(FixedMaterial.getMaterial(Arrays.asList("PUMPKIN")), FixedMaterial.getMaterial(Arrays.asList("PUMPKIN_SEEDS")));
        blockAndItemMaterial.put(FixedMaterial.getMaterial(Arrays.asList("TORCHFLOWER_CROP")), FixedMaterial.getMaterial(Arrays.asList("TORCHFLOWER_SEEDS")));
        blockAndItemMaterial.put(FixedMaterial.getMaterial(Arrays.asList("TORCHFLOWER")), FixedMaterial.getMaterial(Arrays.asList("TORCHFLOWER_SEEDS")));
        blockAndItemMaterial.put(FixedMaterial.getMaterial(Arrays.asList("COCOA")), FixedMaterial.getMaterial(Arrays.asList("COCOA_BEANS")));
        blockAndItemMaterial.put(Material.TRIPWIRE, Material.STRING);
        blockAndItemMaterial.put(Material.REDSTONE_WIRE, Material.REDSTONE);



        addWithoutProblem(oneUsageMaterial, FixedMaterial.getMaterial(Arrays.asList("ENDER_PEARL")));
        addWithoutProblem(oneUsageMaterial, FixedMaterial.getMaterial(Arrays.asList("EGG")));
        addWithoutProblem(oneUsageMaterial, FixedMaterial.getMaterial(Arrays.asList("SNOWBALL")));
        addWithoutProblem(oneUsageMaterial, FixedMaterial.getMaterial(Arrays.asList("SPLASH_POTION")));
        addWithoutProblem(oneUsageMaterial, FixedMaterial.getMaterial(Arrays.asList("LINGERING_POTION")));
        addWithoutProblem(oneUsageMaterial, FixedMaterial.getMaterial(Arrays.asList("BLUE_EGG")));
        addWithoutProblem(oneUsageMaterial, FixedMaterial.getMaterial(Arrays.asList("BROWN_EGG")));
        addWithoutProblem(oneUsageMaterial, FixedMaterial.getMaterial(Arrays.asList("WIND_CHARGE")));
        addWithoutProblem(oneUsageMaterial, FixedMaterial.getMaterial(Arrays.asList("FIREWORK_ROCKET")));

    }

    public void addWithoutProblem(List<Material> list, Material material) {
        if (material == Material.BARRIER) return;

        list.add(material);
    }

    public static ToolsListMaterial getInstance() {
        if (instance == null) return instance = new ToolsListMaterial();
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

}
