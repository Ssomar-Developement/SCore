package com.ssomar.score.usedapi;

import java.util.UUID;

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

		UUID pUUID = p.getUniqueId();

		return area.getOwnerUID().equals(pUUID) || area.isTrusted(pUUID);
	}
}
