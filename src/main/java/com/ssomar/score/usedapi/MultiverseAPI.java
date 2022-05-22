package com.ssomar.score.usedapi;


import com.onarandombox.MultiverseCore.api.Core;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.ssomar.score.SCore;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class MultiverseAPI {

    public static World getWorld(String worldStr){

        Core core;
        try {
            core = (Core) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        } catch (NoClassDefFoundError e) {
            SCore.hasMultiverse = false;
            return null;
        }
        MVWorldManager multiverseManager = core.getMVWorldManager();
        MultiverseWorld mv;
        if((mv = multiverseManager.getMVWorld(worldStr)) != null)
        return mv.getCBWorld();
        else return null;

    }
}
