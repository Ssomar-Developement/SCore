package com.ssomar.score.usedapi;

import com.ssomar.score.SCore;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AllWorldManager {

    public static Optional<World> getWorld(String worldStr) {

        if (SCore.hasMultiverse) {
            World world = MultiverseAPI.getWorld(worldStr);
            if (world != null) return Optional.of(world);
        }

        if(SCore.hasBentoBox){
            World world = BentoBoxAPI.getWorld(worldStr);
            if(world != null) return Optional.of(world);
        }


        return Optional.ofNullable(Bukkit.getServer().getWorld(worldStr));
    }

    public static List<String> getWorlds() {
        List<String> worlds = new ArrayList<>();

        if (SCore.hasMultiverse) {
            worlds.addAll(MultiverseAPI.getWorlds());
        }

        if(SCore.hasBentoBox){
            for(String s : BentoBoxAPI.getWorlds()){
                if(!worlds.contains(s)) worlds.add(s);
            }
        }

        for(World w : Bukkit.getWorlds()){
            if(!worlds.contains(w.getName())){
                worlds.add(w.getName());
            }
        }

        return worlds;
    }
}
