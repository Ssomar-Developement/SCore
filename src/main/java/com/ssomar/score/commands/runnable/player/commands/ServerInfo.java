package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ServerInfo extends PlayerCommand {

    public ServerInfo() {
        CommandSetting showInChat = new CommandSetting("showInChat", 0, Boolean.class, true, true);
        List<CommandSetting> settings = getSettings();
        settings.add(showInChat);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        Boolean showInChat = (Boolean) sCommandToExec.getSettingValue("showInChat");
        if (showInChat == null) showInChat = true;

        double tps = getRecentTps();
        int ping = receiver.getPing();
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        int maxPlayers = Bukkit.getMaxPlayers();

        Runtime runtime = Runtime.getRuntime();
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
        long maxMemory = runtime.maxMemory() / 1024 / 1024;

        String tpsColor = getTpsColor(tps);

        String pingColor = getPingColor(ping);

        if (showInChat) {
            sm.sendMessage(receiver, "&6&l--- Server Information ---", false);
            sm.sendMessage(receiver, "&eTPS: " + tpsColor + String.format("%.2f", tps), false);
            sm.sendMessage(receiver, "&eYour Ping: " + pingColor + ping + "ms", false);
            sm.sendMessage(receiver, "&ePlayers Online: &f" + onlinePlayers + "/" + maxPlayers, false);
            sm.sendMessage(receiver, "&eMemory Usage: &f" + usedMemory + "MB / " + maxMemory + "MB", false);
            sm.sendMessage(receiver, "&6&l---------------------------", false);
        }
    }

    private double getRecentTps() {
        try {
            Object server = Bukkit.getServer().getClass().getMethod("getServer").invoke(Bukkit.getServer());
            double[] recentTps = (double[]) server.getClass().getField("recentTps").get(server);
            return Math.min(recentTps[0], 20.0);
        } catch (Exception e) {
            return 20.0;
        }
    }

    private String getTpsColor(double tps) {
        if (tps >= 18.0) {
            return "&a";
        } else if (tps >= 15.0) {
            return "&e";
        } else if (tps >= 10.0) {
            return "&6";
        } else {
            return "&c";
        }
    }

    private String getPingColor(int ping) {
        if (ping < 50) {
            return "&a";
        } else if (ping < 100) {
            return "&e";
        } else if (ping < 200) {
            return "&6";
        } else {
            return "&c";
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SERVER_INFO");
        names.add("SERVERINFO");
        names.add("TPS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SERVER_INFO [showInChat:true]";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.AQUA;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_AQUA;
    }
}
