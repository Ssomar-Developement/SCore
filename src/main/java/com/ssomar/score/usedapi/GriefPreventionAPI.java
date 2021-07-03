package com.ssomar.score.usedapi;

import java.util.UUID;

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
		
		UUID pUUID = p.getUniqueId();

		return claim.getOwnerID().equals(pUUID);
	}

}
