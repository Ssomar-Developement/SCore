package com.ssomar.score.features;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

@Getter
public class FeatureForItemArgs {

    @Setter
    private @Nullable ItemStack item;

    private @Nullable ItemMeta meta;

    private @Nullable Material material;

    public FeatureForItemArgs(ItemMeta meta, Material material) {
        this.meta = meta;
        this.material = material;
    }

    public FeatureForItemArgs(ItemStack item) {
        this.item = item;
    }


    public static FeatureForItemArgs create(ItemMeta meta, Material material) {
        return new FeatureForItemArgs(meta, material);
    }

    public static FeatureForItemArgs create(ItemMeta meta) {
        return new FeatureForItemArgs(meta, null);
    }

    public static FeatureForItemArgs create(ItemStack item) {
        return new FeatureForItemArgs(item);
    }
}
