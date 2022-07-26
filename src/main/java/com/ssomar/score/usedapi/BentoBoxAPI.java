package com.ssomar.score.usedapi;

import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.database.objects.Island;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
}
