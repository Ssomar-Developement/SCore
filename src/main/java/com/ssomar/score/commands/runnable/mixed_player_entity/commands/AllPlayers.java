package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AllPlayers extends MixedCommand {

    public AllPlayers() {
        setCanExecuteCommands(true);
    }

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                CommmandThatRunsCommand.runPlayerCommands(Bukkit.getOnlinePlayers(), args, aInfo);
            }
        };
        SCore.schedulerHook.runTask(runnable, 0);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ALL_PLAYERS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ALL_PLAYERS {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_PURPLE;
    }

}
