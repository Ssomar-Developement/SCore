package com.ssomar.score.usedapi;


import com.ssomar.score.SCore;
import com.ssomar.score.variables.manager.VariablesManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPISCoreExpansion extends PlaceholderExpansion {

    private final SCore plugin;

    public PlaceholderAPISCoreExpansion(SCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "Ssomar";
    }

    @Override
    public String getIdentifier() {
        return "SCore";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        //System.out.println("params: "+params);

        return VariablesManager.getInstance().onRequestPlaceholder(player, params);
    }
}
