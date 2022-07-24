package com.ssomar.score.usedapi;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GriefPreventionAPI {

    public static boolean playerIsInHisClaim(@NotNull Player p, Location location) {
        return playerCanBreakClaimBlock(p.getUniqueId(), location);
    }

    public static boolean playerIsInHisClaim(@NotNull UUID pUUID, Location location) {
        DataStore dataStore = GriefPrevention.instance.dataStore;
        Claim claim = dataStore.getClaimAt(location, false, null);
        if (claim == null || claim.getOwnerID() == null) return false;

        return claim.getOwnerID().equals(pUUID);
    }


    public static boolean playerCanBreakClaimBlock(@NotNull UUID pUUID, @NotNull Location location) {
        DataStore dataStore = GriefPrevention.instance.dataStore;
        Claim claim = dataStore.getClaimAt(location, false, null);

        if (claim == null) return true;

        Player player = Bukkit.getPlayer(pUUID);
        if (player == null) return false;

        return claim.allowBreak(player, location.getBlock().getType()) == null;
    }

    public static boolean playerCanPlaceClaimBlock(@NotNull UUID pUUID, @NotNull Location location) {
        DataStore dataStore = GriefPrevention.instance.dataStore;
        Claim claim = dataStore.getClaimAt(location, false, null);

        if (claim == null) return true;

        Player player = Bukkit.getPlayer(pUUID);
        if (player == null) return false;

        return claim.allowBuild(player, Material.STONE) == null;
    }

}
