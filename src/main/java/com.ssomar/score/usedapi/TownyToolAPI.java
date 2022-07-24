package com.ssomar.score.usedapi;

import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.claim.Claim;
import com.griefdefender.api.claim.ClaimManager;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TownyToolAPI {

    public static boolean playerIsInHisTown(@NotNull Player player, Location location) {

        Town town = null;
        try {
            town = TownyAPI.getInstance().getTownBlock(location).getTown();
        } catch (NotRegisteredException e) {
            return false;
        }

        return town.hasResident(player.getName());
    }
}
