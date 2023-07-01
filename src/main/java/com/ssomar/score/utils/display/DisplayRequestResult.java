package com.ssomar.score.utils.display;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class DisplayRequestResult {

    private ItemStack itemStack;

    private DisplayResult result;

    public DisplayRequestResult(ItemStack itemStack, DisplayResult result) {
        this.itemStack = itemStack;
        this.result = result;
    }
}
