package com.ssomar.score.usedapi;


import com.onarandombox.MultiverseCore.api.Core;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class MultiverseAPI {

    public static World getWorld(String worldStr){

        Core core = (Core) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager multiverseManager = core.getMVWorldManager();
        MultiverseWorld mv;
        if((mv = multiverseManager.getMVWorld(worldStr)) != null)
        return mv.getCBWorld();
        else
            return null;

    }
}
