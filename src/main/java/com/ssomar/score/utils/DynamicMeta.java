package com.ssomar.score.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.meta.ItemMeta;

public class DynamicMeta {
    @Getter
    @Setter
    private ItemMeta meta;

    public DynamicMeta(ItemMeta meta) {
        this.meta = meta;
    }
}
