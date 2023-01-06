package com.ssomar.score.usedapi;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.land.Area;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LandsIntegrationAPI {

    private final LandsIntegration landsIntegration;

    public LandsIntegrationAPI(Plugin yourPlugin) {
        // view methods of this class: https://github.com/Angeschossen/LandsAPI/blob/4.8.17/src/main/java/me/angeschossen/lands/api/integration/LandsIntegrator.java
        this.landsIntegration = LandsIntegration.of(yourPlugin);
    }

    public boolean playerIsInHisClaim(@NotNull Player p, Location location, boolean acceptWilderness) {

        // get a land area from a location
        final Area area = landsIntegration.getArea(location);
        if (area == null) return acceptWilderness;

        UUID pUUID = p.getUniqueId();

        return area.getOwnerUID().equals(pUUID) || area.isTrusted(pUUID);
    }

    public boolean playerCanBreakClaimBlock(@NotNull UUID pUUID, @NotNull Location location) {
        // get a land area from a location
        final Area area = landsIntegration.getArea(location);

        if (area == null) return true;

        return area.getOwnerUID().equals(pUUID) || area.isTrusted(pUUID);
    }

    public boolean playerCanPlaceClaimBlock(@NotNull UUID pUUID, @NotNull Location location) {
        // get a land area from a location
        final Area area = landsIntegration.getArea(location);

        if (area == null) return true;

        return area.getOwnerUID().equals(pUUID) || area.isTrusted(pUUID);
    }
}
