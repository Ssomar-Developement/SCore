package com.ssomar.score.usedapi;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.database.objects.Island;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BentoBoxAPI {

    public static boolean playerIsOnHisIsland(Player player) {
        return playerIsOnHisIsland(player.getUniqueId(), player.getLocation());
    }

    public static boolean playerIsOnHisIsland(@NotNull UUID pUUID, @NotNull Location location) {
        Optional<Island> islandOpt = BentoBox.getInstance().getIslandsManager().getIslandAt(location);
        if (islandOpt.isPresent()) {
            Island island = islandOpt.get();
            if (island.getMemberSet().contains(pUUID)) {
                return true;
            }

            return false;
        }

        /* He is not on an Island */
        return true;
    }

    public static boolean playerCanBreakIslandBlock(@NotNull UUID pUUID, @NotNull Location location) {
        return playerIsOnHisIsland(pUUID, location);
    }

    public static boolean playerCanPlaceIslandBlock(@NotNull UUID pUUID, @NotNull Location location) {
        return playerIsOnHisIsland(pUUID, location);
    }

    public static World getWorld(String worldStr) {

        for(World w : BentoBox.getInstance().getIWM().getWorlds()){
            if (w.getName().equalsIgnoreCase(worldStr)) {
                return w;
            }
        }
        return null;
    }

    public static List<String> getWorlds() {
        List<String> worlds = new ArrayList<>();
        for(World w : BentoBox.getInstance().getIWM().getWorlds()){
            worlds.add(w.getName());
        }
        return worlds;
    }
}
