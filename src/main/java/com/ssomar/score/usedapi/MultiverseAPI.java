package com.ssomar.score.usedapi;


import com.onarandombox.MultiverseCore.api.Core;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.ssomar.score.SCore;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class MultiverseAPI {

    public static World getWorld(String worldStr) {

        Core core;
        try {
            core = (Core) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        } catch (NoClassDefFoundError e) {
            SCore.hasMultiverse = false;
            for (World w : Bukkit.getWorlds()) {
                if (w.getName().equalsIgnoreCase(worldStr)) {
                    return w;
                }
            }
            return null;
        }
        MVWorldManager multiverseManager = core.getMVWorldManager();
        MultiverseWorld mv;
        if ((mv = multiverseManager.getMVWorld(worldStr)) != null)
            return mv.getCBWorld();
        else return null;

    }

    public static List<String> getWorlds() {
        List<String> worlds = new ArrayList<>();
        Core core;
        try {
            core = (Core) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        } catch (NoClassDefFoundError e) {
            SCore.hasMultiverse = false;
            for(World w : Bukkit.getWorlds()) {
                worlds.add(w.getName());
            }
            return worlds;
        }
        MVWorldManager multiverseManager = core.getMVWorldManager();
        for (MultiverseWorld mv : multiverseManager.getMVWorlds()) {
            worlds.add(mv.getName());
        }
        return worlds;
    }
}
