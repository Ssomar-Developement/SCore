package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NearbyPlayers extends PlayerCommand {

    public NearbyPlayers() {
        CommandSetting radius = new CommandSetting("radius", 0, Integer.class, 50, true);
        CommandSetting showDistance = new CommandSetting("showDistance", 1, Boolean.class, true, true);
        CommandSetting showDirection = new CommandSetting("showDirection", 2, Boolean.class, true, true);
        List<CommandSetting> settings = getSettings();
        settings.add(radius);
        settings.add(showDistance);
        settings.add(showDirection);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        Integer radius = (Integer) sCommandToExec.getSettingValue("radius");
        Boolean showDistance = (Boolean) sCommandToExec.getSettingValue("showDistance");
        Boolean showDirection = (Boolean) sCommandToExec.getSettingValue("showDirection");

        if (radius == null) radius = 50;
        if (showDistance == null) showDistance = true;
        if (showDirection == null) showDirection = true;

        if (radius < 1) radius = 1;
        if (radius > 500) radius = 500;

        Location receiverLoc = receiver.getLocation();
        List<PlayerInfo> nearbyPlayers = new ArrayList<>();

        for (Entity entity : receiver.getNearbyEntities(radius, radius, radius)) {
            if (entity instanceof Player && !entity.equals(receiver)) {
                Player nearbyPlayer = (Player) entity;
                double distance = receiverLoc.distance(nearbyPlayer.getLocation());
                String direction = getDirection(receiverLoc, nearbyPlayer.getLocation());
                nearbyPlayers.add(new PlayerInfo(nearbyPlayer.getName(), distance, direction));
            }
        }

        nearbyPlayers.sort(Comparator.comparingDouble(info -> info.distance));

        sm.sendMessage(receiver, "&6&l--- Nearby Players (Radius: " + radius + ") ---", false);

        if (nearbyPlayers.isEmpty()) {
            sm.sendMessage(receiver, "&7No players found nearby.", false);
        } else {
            final Boolean finalShowDistance = showDistance;
            final Boolean finalShowDirection = showDirection;

            for (PlayerInfo info : nearbyPlayers) {
                StringBuilder message = new StringBuilder("&e" + info.name);
                if (finalShowDistance) {
                    message.append(" &7- &f").append(String.format("%.1f", info.distance)).append(" blocks");
                }
                if (finalShowDirection) {
                    message.append(" &7(").append(info.direction).append(")");
                }
                sm.sendMessage(receiver, message.toString(), false);
            }
        }

        sm.sendMessage(receiver, "&6&l" + "-".repeat(35), false);
    }

    private String getDirection(Location from, Location to) {
        double dx = to.getX() - from.getX();
        double dz = to.getZ() - from.getZ();

        double angle = Math.toDegrees(Math.atan2(-dx, dz));
        if (angle < 0) angle += 360;

        if (angle >= 337.5 || angle < 22.5) return "N";
        else if (angle < 67.5) return "NE";
        else if (angle < 112.5) return "E";
        else if (angle < 157.5) return "SE";
        else if (angle < 202.5) return "S";
        else if (angle < 247.5) return "SW";
        else if (angle < 292.5) return "W";
        else return "NW";
    }

    private static class PlayerInfo {
        final String name;
        final double distance;
        final String direction;

        PlayerInfo(String name, double distance, String direction) {
            this.name = name;
            this.distance = distance;
            this.direction = direction;
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("NEARBY_PLAYERS");
        names.add("NEARBYPLAYERS");
        names.add("NEARBY");
        return names;
    }

    @Override
    public String getTemplate() {
        return "NEARBY_PLAYERS [radius:50] [showDistance:true] [showDirection:true]";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GREEN;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_GREEN;
    }
}
