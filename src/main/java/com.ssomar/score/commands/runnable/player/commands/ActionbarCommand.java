package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ssomar.score.actionbar.Actionbar;
import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;

public class ActionbarCommand extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        if (args.size() >= 2) {

            StringBuilder name = new StringBuilder();

            for (int i = 0; i < args.size() - 1; i++) {
                if (i == 0) name = new StringBuilder(args.get(i));
                else name.append(" ").append(args.get(i));
            }

            try {
                int time = Integer.parseInt(args.get(args.size() - 1));
                ActionbarHandler.getInstance().addActionbar(receiver, new Actionbar(name.toString(), time));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";
        return  error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ACTIONBAR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ACTIONBAR {name} {time in secs}";
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
