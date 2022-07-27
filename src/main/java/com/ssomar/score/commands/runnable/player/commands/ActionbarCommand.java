package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.actionbar.Actionbar;
import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActionbarCommand extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < args.size() - 1; i++) {
            if (i == 0) name = new StringBuilder(args.get(i));
            else name.append(" ").append(args.get(i));
        }

        int time = Double.valueOf(args.get(args.size() - 1)).intValue();
        ActionbarHandler.getInstance().addActionbar(receiver, new Actionbar(name.toString(), time));
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (args.size() < 3) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(args.size() - 1), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
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
