package com.ssomar.score.utils;

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
    ALL_FISH,
    ALL_BEDS,
    ALL_TERRACOTTA,
    ALL_NORMAL_TERRACOTTA,
    ALL_GLAZED_TERRACOTTA,
    ALL_CONCRETE,
    ALL_GLASS,
    ALL_STAINED_GLASS,
    ALL_STAINED_GLASS_PANES,
    ALL_SHULKER_BOXES;

    public static boolean verif(Material material, String name){
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

    public static List<String> getMaterialWithGroupsList() {
        SortedMap<String, String> map = new TreeMap<String, String>();
        for (MaterialWithGroups materialGroup : MaterialWithGroups.values()) {
            map.put(materialGroup.name(), materialGroup.name());
        }
        for(Material material : Material.values()) {
            if(material.isItem() && !material.isAir()) map.put(material.name(), material.name());
        }
        return new ArrayList<>(map.values());
    }

    public static Material getMaterial(String name) {
        name = name.toUpperCase();
        for (Material material : Material.values()) {
            if (material.name().equals(name)) {
                return material;
            }
        }
        return Material.STONE;
    }
}
