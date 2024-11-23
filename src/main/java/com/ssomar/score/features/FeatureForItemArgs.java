package com.ssomar.score.features;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class FeatureForItemArgs {

    private @NotNull ItemMeta meta;

    private @Nullable Material material;

    public FeatureForItemArgs(ItemMeta meta, Material material) {
        this.meta = meta;
        this.material = material;
    }

    public static FeatureForItemArgs create(ItemMeta meta, Material material) {
        return new FeatureForItemArgs(meta, material);
    }

    public static FeatureForItemArgs create(ItemMeta meta) {
        return new FeatureForItemArgs(meta, null);
    }
}
