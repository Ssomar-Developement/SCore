package com.ssomar.score.utils;

import com.ssomar.score.SCore;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class FixedMaterial {

    public static Material getMaterial(List<String> materials) {
        for (String mat : materials) {
            try {
                return Material.valueOf(mat.toUpperCase());
            } catch (Exception e) {
                continue;
            }
        }
        return Material.BARRIER;
    }

    public static void addMaterial(List<String> materials, List<Material> list) {
        for (String mat : materials) {
            try {
                list.add(Material.valueOf(mat.toUpperCase()));
            } catch (Exception e) {
                continue;
            }
        }
    }

    public static Material getHead() {
        return FixedMaterial.getMaterial(Arrays.asList("PLAYER_HEAD", "SKULL_ITEM"));
    }

    public static Material getBrewingStand() {
        if (!SCore.is1v8()) return FixedMaterial.getMaterial(Arrays.asList("BREWING_STAND"));
        else return FixedMaterial.getMaterial(Arrays.asList("BREWING_STAND_ITEM"));
    }
}
