package com.ssomar.score.usedapi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Area;

public class LandsIntegrationAPI {

	private final LandsIntegration landsIntegration;

	public LandsIntegrationAPI(Plugin yourPlugin) {

		// view methods of this class: https://github.com/Angeschossen/LandsAPI/blob/4.8.17/src/main/java/me/angeschossen/lands/api/integration/LandsIntegrator.java
		this.landsIntegration = new LandsIntegration(yourPlugin);
	}

	public boolean playerIsInHisClaim(@NotNull Player p, Location location) {

		// get a land area from a location
		final Area area = landsIntegration.getAreaByLoc(location);
		if(area == null) return false;

		UUID pUUID = p.getUniqueId();

		return area.getOwnerUID().equals(pUUID) || area.isTrusted(pUUID);
	}

	public boolean playerCanBreakClaimBlock(@NotNull UUID pUUID, @NotNull Location location) {
		// get a land area from a location
		final Area area = landsIntegration.getAreaByLoc(location);

		if(area == null) return true;

		return area.getOwnerUID().equals(pUUID) || area.isTrusted(pUUID);
	}
}
