package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BackDash extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        int amount;

        try {
            amount = Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            return;
        }

        Location pLoc = entity.getLocation();
        pLoc.setPitch(0);
        Vector v = pLoc.getDirection();
        v.multiply(-amount);
        entity.setVelocity(v);

    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String frontdash = "BACKDASH {number}";
        if (args.size() > 1) error = tooManyArgs + frontdash;
        else if (args.size() < 1) error = notEnoughArgs + frontdash;
        else if (args.size() == 1) {
            try {
                Integer.valueOf(args.get(0));
            } catch (NumberFormatException e) {
                error = invalidTime + args.get(0) + " for command: " + frontdash;
            }
        }

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("BACKDASH");
        return names;
    }

    @Override
    public String getTemplate() {
        return "BACKDASH {number}";
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
