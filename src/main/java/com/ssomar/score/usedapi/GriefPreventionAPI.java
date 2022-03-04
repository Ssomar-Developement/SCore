package com.ssomar.score.usedapi;

import java.util.UUID;

import me.ryanhamshire.GriefPrevention.ClaimPermission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

public class GriefPreventionAPI {
	
	public static boolean playerIsInHisClaim(@NotNull Player p, Location location) {

		DataStore dataStore = GriefPrevention.instance.dataStore;
		Claim claim = dataStore.getClaimAt(location, false, null);
		if(claim == null) return false;
		
		UUID pUUID = p.getUniqueId();

		return claim.getOwnerID().equals(pUUID);
	}

	public static boolean playerCanBreakClaimBlock(@NotNull UUID pUUID, @NotNull Location location) {

		DataStore dataStore = GriefPrevention.instance.dataStore;
		Claim claim = dataStore.getClaimAt(location, false, null);
		if(claim == null) return true;

		return claim.getOwnerID().equals(pUUID);
	}

}
