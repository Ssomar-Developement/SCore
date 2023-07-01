package com.ssomar.score.utils.display;


import com.ssomar.score.splugin.SPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DisplayModule {
    private final int weight;

    private final SPlugin plugin;

    protected DisplayModule(@NotNull SPlugin plugin, @NotNull DisplayPriority priority) {
        this(plugin, priority.getWeight());
    }

    protected DisplayModule(@NotNull SPlugin plugin, int weight) {
        this.plugin = plugin;
        this.weight = weight;
    }

    public boolean display(@NotNull ItemStack itemStack) {return false;}

    public boolean display(@NotNull ItemStack itemStack, @Nullable Player player) {return false;}

    public boolean display(@NotNull ItemStack itemStack, @Nullable Player player, @NotNull DisplayProperties properties) {return false;}

    public void revert(@NotNull ItemStack itemStack) {}

    public final String getPluginName() {
        return getPlugin().getName();
    }

    public int getWeight() {
        return this.weight;
    }

    public SPlugin getPlugin() {
        return this.plugin;
    }
}

