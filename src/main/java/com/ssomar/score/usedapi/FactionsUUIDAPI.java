package com.ssomar.score.usedapi;


import com.massivecraft.factions.*;
import com.ssomar.score.SsomarDev;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class FactionsUUIDAPI {

    public boolean playerIsInHisClaim(@NotNull UUID pUUID, Location location, boolean acceptWilderness) {
        SsomarDev.testMsg("PASSSEEE 1", true);

        FLocation fLocation = new FLocation(location);
        // get a land area from a location
        final Faction area = Board.getInstance().getFactionAt(fLocation);
        if (area == null || area.isWilderness()) return acceptWilderness;

        FPlayer fplayer = FPlayers.getInstance().getById(pUUID.toString());

        return area.getFPlayers().contains(fplayer);

    }

    public boolean playerCanBreakClaimBlock(@NotNull UUID pUUID, @NotNull Location location) {

        return playerIsInHisClaim(pUUID, location, true);

        /*
        final Area area = landsIntegration.getArea(location);

        if (area == null) return true;

        return area.getOwnerUID().equals(pUUID) || area.isTrusted(pUUID);*/
    }

    public boolean playerCanPlaceClaimBlock(@NotNull UUID pUUID, @NotNull Location location) {
        return playerIsInHisClaim(pUUID, location, true);
    }
}
