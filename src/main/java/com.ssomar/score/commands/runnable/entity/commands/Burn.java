package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;

/* BURN {timeinsecs} */
public class Burn extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        try {
            if (args.size() == 0) {
                entity.setFireTicks(20 * 10);
            } else {
                double time = Double.parseDouble(args.get(0));
                entity.setFireTicks(20 * (int) time);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String burn = "BURN {timeinsecs}";
        if (args.size() > 1) error = tooManyArgs + burn;
        else if (args.size() == 1) {
            try {
                Double.valueOf(args.get(0));
            } catch (NumberFormatException e) {
                error = invalidTime + args.get(0) + " for command: " + burn;
            }
        }

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("BURN");
        return names;
    }

    @Override
    public String getTemplate() {
        return "BURN {timeinsecs}";
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
