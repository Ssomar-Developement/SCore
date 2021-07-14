package com.ssomar.score.usedapi;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.claim.Claim;
import com.griefdefender.api.claim.ClaimManager;

public class GriefDefenderAPI {
	
	public static boolean playerIsInHisClaim(@NotNull Player p, Location location) {

		ClaimManager cM = GriefDefender.getCore().getClaimManager(location.getWorld().getUID());
		
		Claim claim = cM.getClaimAt((int) location.getX(), (int) location.getY(), (int) location.getZ());
		
		if(claim.isWilderness()) return false;
		
		return claim.getOwnerUniqueId().equals(p.getUniqueId()) || claim.getUserTrusts().contains(p.getUniqueId());
	}

}
