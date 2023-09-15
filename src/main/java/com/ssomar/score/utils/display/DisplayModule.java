package com.ssomar.score.utils.display;


import com.ssomar.score.splugin.SPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class DisplayModule {
    private final int weight;
    private final SPlugin plugin;

    private List<String> loadedIDs;

    protected DisplayModule(@NotNull SPlugin plugin, @NotNull DisplayPriority priority) {
        this(plugin, priority.getWeight());
        loadedIDs = new ArrayList<>();
    }

    protected DisplayModule(@NotNull SPlugin plugin, int weight) {
        this.plugin = plugin;
        this.weight = weight;
        loadedIDs = new ArrayList<>();
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

    public List<String> getLoadedIDs() {
        return loadedIDs;
    }

    public void addLoadedID(String id) {
        if (!loadedIDs.contains(id)) loadedIDs.add(id);
    }

    public String getLoadedIDsString() {
    	String result = "";
    	for(String id : loadedIDs) {
    		result += id+", ";
    	}
        /* remove the last ,*/
        if(result.length() > 2) result = result.substring(0, result.length()-2);
    	return result;
    }
}

