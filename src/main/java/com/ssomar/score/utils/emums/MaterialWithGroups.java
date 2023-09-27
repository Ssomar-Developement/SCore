package com.ssomar.score.utils.emums;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.FixedMaterial;
import org.bukkit.Material;

import java.util.*;

public enum MaterialWithGroups {
    ALL_CHESTS,
    ALL_FURNACES,
    ALL_PLANKS,
    ALL_LOGS,
    ALL_WOODS,
    ALL_ORES,
    ALL_WOOLS,
    ALL_SLABS,
    ALL_STAIRS,
    ALL_FENCES,
    ALL_SAPLINGS,
    ALL_CROPS,
    ALL_DOORS,
    ALL_TRAPDOORS,
    ALL_BEDS,
    ALL_TERRACOTTA,
    ALL_NORMAL_TERRACOTTA,
    ALL_GLAZED_TERRACOTTA,
    ALL_CONCRETE,
    ALL_CONCRETE_POWDERS,
    ALL_GLASS,
    ALL_STAINED_GLASS,
    ALL_SHULKER_BOXES,
    ALL_LEAVES,
    ALL_CARPETS;

    public static boolean verif(Material material, String name) {
        name = name.toUpperCase();
        MaterialWithGroups materialWithGroups = null;
        for (MaterialWithGroups materialGroup : MaterialWithGroups.values()) {
            if (materialGroup.name().equals(name)) {
                materialWithGroups = materialGroup;
            }
        }
        if (materialWithGroups == null) {
            try {
                return material.equals(Material.valueOf(name));
            } catch (Exception e) {
                return false;
            }
        } else {
            switch (materialWithGroups) {
                case ALL_CHESTS:
                    List<Material> chests = new ArrayList<>();
                    FixedMaterial.addMaterial(Arrays.asList("CHEST", "TRAPPED_CHEST", "ENDER_CHEST"), chests);
                    return chests.contains(material);
                case ALL_FURNACES:
                    List<Material> furnaces = new ArrayList<>();
                    FixedMaterial.addMaterial(Arrays.asList("FURNACE", "FURNACE_MINECART", "BLAST_FURNACE"), furnaces);
                    return furnaces.contains(material);
                case ALL_PLANKS:
                    List<Material> planks = new ArrayList<>();
                    FixedMaterial.addMaterial(Arrays.asList("MANGROVE_PLANKS", "WARPED_PLANKS", "OAK_PLANKS", "SPRUCE_PLANKS", "BIRCH_PLANKS", "JUNGLE_PLANKS", "ACACIA_PLANKS", "DARK_OAK_PLANKS", "CRIMSON_PLANKS", "CHERRY_PLANKS"), planks);
                    return planks.contains(material);
                case ALL_LOGS:
                    List<Material> logs = new ArrayList<>();
                    FixedMaterial.addMaterial(Arrays.asList("MANGROVE_LOG", "WARPED_LOG", "OAK_LOG", "SPRUCE_LOG", "BIRCH_LOG", "JUNGLE_LOG", "ACACIA_LOG", "DARK_OAK_LOG", "CRIMSON_LOG", "CHERRY_LOG"), logs);
                    return logs.contains(material);
                case ALL_WOODS:
                    List<Material> woods = new ArrayList<>();
                    FixedMaterial.addMaterial(Arrays.asList("MANGROVE_WOOD", "WARPED_WOOD", "OAK_WOOD", "SPRUCE_WOOD", "BIRCH_WOOD", "JUNGLE_WOOD", "ACACIA_WOOD", "DARK_OAK_WOOD", "CRIMSON_WOOD", "CHERY_WOOD"), woods);
                    return woods.contains(material);
                case ALL_ORES:
                    List<Material> ores = new ArrayList<>();
                    FixedMaterial.addMaterial(Arrays.asList("COAL_ORE", "DEEPSLATE_COAL_ORE", "IRON_ORE", "DEEPSLATE_IRON_ORE", "COPPER_ORE", "DEEPSLATE_COPPER_ORE",
                            "GOLD_ORE", "DEEPSLATE_GOLD_ORE", "REDSTONE_ORE", "DEEPSLATE_REDSTONE_ORE", "EMERALD_ORE", "DEEPSLATE_EMERALD_ORE", "LAPIS_ORE", "DEEPSLATE_LAPIS_ORE"
                            , "DIAMOND_ORE", "DEEPSLATE_DIAMOND_ORE", "NETHER_GOLD_ORE", "NETHER_QUARTZ_ORE"), ores);
                    return ores.contains(material);
                case ALL_WOOLS:
                    List<Material> wools = new ArrayList<>();
                    FixedMaterial.addMaterial(Arrays.asList("WHITE_WOOL", "ORANGE_WOOL", "MAGENTA_WOOL", "LIGHT_BLUE_WOOL", "YELLOW_WOOL", "LIME_WOOL", "PINK_WOOL", "GRAY_WOOL", "LIGHT_GRAY_WOOL", "CYAN_WOOL", "PURPLE_WOOL", "BLUE_WOOL", "BROWN_WOOL", "GREEN_WOOL", "RED_WOOL", "BLACK_WOOL"), wools);
                    return wools.contains(material);
                case ALL_SLABS:
                    List<Material> slabs = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("SLAB")) {
                            slabs.add(material1);
                        }
                    }
                    return slabs.contains(material);
                case ALL_STAIRS:
                    List<Material> stairs = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("STAIRS")) {
                            stairs.add(material1);
                        }
                    }
                    return stairs.contains(material);
                case ALL_FENCES:
                    List<Material> fences = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("FENCE")) {
                            fences.add(material1);
                        }
                    }
                    return fences.contains(material);
                case ALL_SAPLINGS:
                    List<Material> saplings = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("SAPLING")) {
                            saplings.add(material1);
                        }
                    }
                    return saplings.contains(material);
                case ALL_CROPS:
                    List<Material> crops = new ArrayList<>();
                    FixedMaterial.addMaterial(Arrays.asList("CROPS", "NETHER_WARTS", "POTATO", "CARROT", "BEETROOT_BLOCK", "WHEAT", "CARROTS", "BEETROOTS", "POTATOES", "NETHER_WART"), crops);
                    return crops.contains(material);
                case ALL_DOORS:
                    List<Material> doors = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("DOOR") && !material1.name().contains("TRAPDOOR")) {
                            doors.add(material1);
                        }
                    }
                    return doors.contains(material);
                case ALL_TRAPDOORS:
                    List<Material> trapdoors = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("TRAPDOOR")) {
                            trapdoors.add(material1);
                        }
                    }
                    return trapdoors.contains(material);
                case ALL_BEDS:
                    List<Material> beds = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("BED")) {
                            beds.add(material1);
                        }
                    }
                    return beds.contains(material);
                case ALL_TERRACOTTA:
                    List<Material> terracotta = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("TERRACOTTA")) {
                            terracotta.add(material1);
                        }
                    }
                    return terracotta.contains(material);
                case ALL_NORMAL_TERRACOTTA:
                    List<Material> normalTerracotta = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("TERRACOTTA") && !material1.name().contains("GLAZED")) {
                            normalTerracotta.add(material1);
                        }
                    }
                    return normalTerracotta.contains(material);
                case ALL_GLAZED_TERRACOTTA:
                    List<Material> glazedTerracotta = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("GLAZED_TERRACOTTA")) {
                            glazedTerracotta.add(material1);
                        }
                    }
                    return glazedTerracotta.contains(material);
                case ALL_CONCRETE:
                    List<Material> concrete = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("CONCRETE")) {
                            concrete.add(material1);
                        }
                    }
                    return concrete.contains(material);
                case ALL_CONCRETE_POWDERS:
                    List<Material> concretePowders = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("CONCRETE_POWDER")) {
                            concretePowders.add(material1);
                        }
                    }
                    return concretePowders.contains(material);
                case ALL_GLASS:
                    List<Material> glass = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("GLASS")) {
                            glass.add(material1);
                        }
                    }
                    return glass.contains(material);
                case ALL_STAINED_GLASS:
                    List<Material> stainedGlass = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("STAINED_GLASS")) {
                            stainedGlass.add(material1);
                        }
                    }
                    return stainedGlass.contains(material);
                case ALL_SHULKER_BOXES:
                    List<Material> shulkerBoxes = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("SHULKER_BOX")) {
                            shulkerBoxes.add(material1);
                        }
                    }
                    return shulkerBoxes.contains(material);
                case ALL_LEAVES:
                    List<Material> leaves = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("LEAVES")) {
                            leaves.add(material1);
                        }
                    }
                    return leaves.contains(material);
                case ALL_CARPETS:
                    List<Material> carpets = new ArrayList<>();
                    for (Material material1 : Material.values()) {
                        if (material1.name().contains("CARPET")) {
                            carpets.add(material1);
                        }
                    }
                    return carpets.contains(material);
            }
        }
        return false;
    }

    public static Optional<String> getMaterialWithGroups(String name) {
        name = name.toUpperCase();
        for (MaterialWithGroups materialGroup : MaterialWithGroups.values()) {
            if (materialGroup.name().equals(name)) {
                return Optional.of(materialGroup.name());
            }
        }

        for (Material material : Material.values()) {
            if (material.name().equals(name)) {
                return Optional.of(material.name());
            }
        }
        return Optional.empty();
    }

    public static List<String> getMaterialWithGroupsList(boolean acceptAir, boolean acceptItem, boolean acceptBlock) {
        SortedMap<String, String> map = new TreeMap<String, String>();
        for (MaterialWithGroups materialGroup : MaterialWithGroups.values()) {
            map.put(materialGroup.name(), materialGroup.name());
        }
        for (Material material : Material.values()) {
            if (
                    (!SCore.is1v12Less() && material.isAir())
                    || (SCore.is1v12Less() && material.equals(Material.AIR))
            ) {
                if (acceptAir) {
                    map.put(material.name(), material.name());
                } else {
                    continue;
                }
            }
            if (acceptItem && material.isItem()) map.put(material.name(), material.name());
            else if (acceptBlock && material.isBlock()) map.put(material.name(), material.name());
        }
        return new ArrayList<>(map.values());
    }

    public static Material getMaterial(String name) {
        name = name.toUpperCase();
        MaterialWithGroups materialWithGroups = null;
        for (MaterialWithGroups materialGroup : MaterialWithGroups.values()) {
            if (materialGroup.name().equals(name)) {
                materialWithGroups = materialGroup;
            }
        }
        if (materialWithGroups == null) {
            for (Material material : Material.values()) {
                if (material.name().equals(name)) {
                    if (SCore.is1v12Less() && material.equals(Material.AIR))return Material.BARRIER;
                    else if (!SCore.is1v12Less() && material.isAir()) return Material.BARRIER;
                    else if (!material.isItem()) return Material.BARRIER;
                    return material;
                }
            }
        } else {
            switch (materialWithGroups) {
                case ALL_CHESTS:
                    return FixedMaterial.getMaterial(Collections.singletonList("CHEST"));
                case ALL_FURNACES:
                    return FixedMaterial.getMaterial(Collections.singletonList("FURNACE"));
                case ALL_PLANKS:
                    return FixedMaterial.getMaterial(Collections.singletonList("OAK_PLANKS"));
                case ALL_LOGS:
                    return FixedMaterial.getMaterial(Collections.singletonList("OAK_LOG"));
                case ALL_WOODS:
                    return FixedMaterial.getMaterial(Collections.singletonList("OAK_WOOD"));
                case ALL_ORES:
                    return FixedMaterial.getMaterial(Collections.singletonList("COAL_ORE"));
                case ALL_WOOLS:
                    return FixedMaterial.getMaterial(Collections.singletonList("LIME_WOOL"));
                case ALL_SLABS:
                    return FixedMaterial.getMaterial(Collections.singletonList("STONE_SLAB"));
                case ALL_STAIRS:
                    return FixedMaterial.getMaterial(Collections.singletonList("STONE_STAIRS"));
                case ALL_FENCES:
                    return FixedMaterial.getMaterial(Collections.singletonList("OAK_FENCE"));
                case ALL_SAPLINGS:
                    return FixedMaterial.getMaterial(Collections.singletonList("OAK_SAPLING"));
                case ALL_CROPS:
                    return FixedMaterial.getMaterial(Arrays.asList("WHEAT", "CROPS"));
                case ALL_DOORS:
                    return FixedMaterial.getMaterial(Collections.singletonList("OAK_DOOR"));
                case ALL_TRAPDOORS:
                    return FixedMaterial.getMaterial(Collections.singletonList("OAK_TRAPDOOR"));
                case ALL_BEDS:
                    return FixedMaterial.getMaterial(Collections.singletonList("OAK_BED"));
                case ALL_TERRACOTTA:
                    return FixedMaterial.getMaterial(Collections.singletonList("TERRACOTTA"));
                case ALL_NORMAL_TERRACOTTA:
                    return FixedMaterial.getMaterial(Collections.singletonList("LIME_TERRACOTTA"));
                case ALL_GLAZED_TERRACOTTA:
                    return FixedMaterial.getMaterial(Collections.singletonList("LIME_GLAZED_TERRACOTTA"));
                case ALL_CONCRETE:
                    return FixedMaterial.getMaterial(Collections.singletonList("LIME_CONCRETE"));
                case ALL_GLASS:
                    return FixedMaterial.getMaterial(Collections.singletonList("GLASS"));
                case ALL_STAINED_GLASS:
                    return FixedMaterial.getMaterial(Collections.singletonList("LIME_STAINED_GLASS"));
                case ALL_SHULKER_BOXES:
                    return FixedMaterial.getMaterial(Collections.singletonList("LIME_SHULKER_BOX"));
                case ALL_LEAVES:
                    return FixedMaterial.getMaterial(Collections.singletonList("OAK_LEAVES"));
                case ALL_CARPETS:
                    return FixedMaterial.getMaterial(Arrays.asList("WHITE_CARPET", "CARPET"));
            }
        }
        return Material.STONE;
    }
}
