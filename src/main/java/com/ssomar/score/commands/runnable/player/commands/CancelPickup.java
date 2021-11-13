package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandManager;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CancelPickup extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        int time = 20;
        try {
            time = Integer.parseInt(args.get(0));
        } catch (Exception ignored) {
        }
        CommandsHandler.getInstance().addStopPickup(receiver, time);
    }

    @Override
    public String verify(List<String> args) {
        String error = "";

        String burn = "CANCELPICKUP {tim_ein_ticks}";
        if (args.size() > 1) error = tooManyArgs + burn;
        else if (args.size() == 1) {
            try {
                Double.valueOf(args.get(0));
            } catch (NumberFormatException e) {
                error = invalidTime + args.get(0) + " for command: " + burn;
            }
        }

        return error;
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CANCELPICKUP");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CANCELPICKUP {time_in_ticks}";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }
}
