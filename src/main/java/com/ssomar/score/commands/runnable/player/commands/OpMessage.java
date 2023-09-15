package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* OPMESSAGE {text} */
public class OpMessage extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        StringBuilder build = new StringBuilder();

        for (String arg : args) {
            build.append(arg + " ");
        }

        System.out.println(build);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.isOp()) {
                player.sendMessage(build.toString());
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("OPMESSAGE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "OPMESSAGE {text}";
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
