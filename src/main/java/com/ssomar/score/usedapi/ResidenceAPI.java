package com.ssomar.score.usedapi;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Area;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ResidenceAPI {

	public static boolean playerIsInHisClaim(@NotNull Player p, Location location) {

		ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);
		if(res == null) return false;

		return res.getOwnerUUID().equals(p.getUniqueId()) || res.isTrusted(p);

	}

	public static boolean playerCanBreakClaimBlock(@NotNull UUID pUUID, @NotNull Location location) {
		ResidencePlayer rPlayer = Residence.getInstance().getPlayerManager().getResidencePlayer(pUUID);
		boolean canBreak = rPlayer.canBreakBlock(location.getBlock(), false);
		return canBreak;
	}

	public static boolean playerCanPlaceClaimBlock(@NotNull UUID pUUID, @NotNull Location location) {
		ResidencePlayer rPlayer = Residence.getInstance().getPlayerManager().getResidencePlayer(pUUID);
		boolean canPlace = rPlayer.canPlaceBlock(location.getBlock(), false);
		return canPlace;
	}
}
