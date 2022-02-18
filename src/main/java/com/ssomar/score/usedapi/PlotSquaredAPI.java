package com.ssomar.score.usedapi;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


public class PlotSquaredAPI {


    public static boolean playerIsInHisPlot(@NotNull Player p, Location location) {

      return  true;

    }


    public static boolean playerCanBreakIslandBlock(@NotNull UUID pUUID, @NotNull Location location) {

      return true;
    }
}
