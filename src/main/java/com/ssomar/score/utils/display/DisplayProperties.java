package com.ssomar.score.utils.display;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class DisplayProperties {
    private final boolean inInventory;

    private final boolean inGui;

    @NotNull
    private final ItemStack originalItem;

    public DisplayProperties(boolean inInventory, boolean inGui, @NotNull ItemStack originalItem) {
        this.inInventory = inInventory;
        this.inGui = inGui;
        this.originalItem = originalItem;
    }

    public boolean inInventory() {
        return this.inInventory;
    }

    public boolean inGui() {
        return this.inGui;
    }

    @NotNull
    public ItemStack originalItem() {
        return this.originalItem;
    }
}