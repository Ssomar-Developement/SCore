package com.ssomar.score.usedapi;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class HeadDatabase {

    private static HeadDatabase instance;
    private final HashMap<String, ItemStack> loadedHeads;

    public HeadDatabase() {
        loadedHeads = new HashMap<>();
    }

    public static HeadDatabase getInstance() {
        if (instance == null) instance = new HeadDatabase();
        return instance;
    }

    public @Nullable
    ItemStack getHead(String id) {
        ItemStack item = null;
        if (loadedHeads.containsKey(id)) {
            //System.out.println("loadedHead: yes "+id);
            item =  loadedHeads.get(id);
        } else {
            HeadDatabaseAPI api = new HeadDatabaseAPI();
            item = api.getItemHead(id);
            // System.out.println("loadedHead: no "+ id);
            if (item != null) {
                //  System.out.println("loadedHead: added: "+id);
                loadedHeads.put(id, item);
            }
        }
        return item.clone();
    }

    public String getBase64(String id) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        return api.getBase64(id);
    }
}
