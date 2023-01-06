package com.ssomar.score.usedapi;


import com.ssomar.score.SCore;
import com.ssomar.score.variables.Variable;
import com.ssomar.score.variables.manager.VariablesManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.util.Optional;

public class SCoreExpansion extends PlaceholderExpansion {

    private final SCore plugin;

    public SCoreExpansion(SCore plugin) {
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

        if (params.startsWith("variables")) {
            String[] args = params.split("_");
            if (args.length >= 2) {
                String text = args[1];
                Optional<Variable> var = VariablesManager.getInstance().getVariable(text);
                if (var.isPresent()) {
                    String value =  var.get().getValue(Optional.ofNullable(player.getPlayer()));
                    if(args[args.length-1].equalsIgnoreCase("int")){
                        try{
                            return Integer.parseInt(value)+"";
                        }catch (NumberFormatException e){
                            return "Variable can't be converted to int";
                        }
                    }
                    else return value;
                }
                return "Variable not found";
            }
        }

        return null; // Placeholder is unknown by the Expansion
    }
}
