package com.ssomar.score.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
@Setter
public class DynamicMeta {

    private ItemMeta meta;
    private Material material;

    public DynamicMeta(ItemMeta meta) {
        this.meta = meta;
        this.material = Material.AIR;
    }

    public DynamicMeta(ItemMeta meta, Material material) {
        this.meta = meta;
        this.material = material;
    }
}
