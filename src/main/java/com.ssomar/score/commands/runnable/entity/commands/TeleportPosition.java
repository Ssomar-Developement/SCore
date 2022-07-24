package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;

/* TELEPORT POSITION {x} {y} {z} */
public class TeleportPosition extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        if (args.size() == 3) {
            try {
                if (!entity.isDead())
                    entity.teleport(new Location(entity.getWorld(), Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), Integer.parseInt(args.get(2))));
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        String error = "";

        String tppos = "TELEPORT POSITION {x} {y} {z}";
        if (args.size() < 3) error = notEnoughArgs + tppos;
        else if (args.size() == 3) {
            boolean bError = false;
            try {
                Double.valueOf(args.get(0));
            } catch (NumberFormatException e) {
                error = invalidMaterial + args.get(0) + " for command: " + tppos;
                bError = true;
            }
            if (!bError) {
                try {
                    Integer.valueOf(args.get(1));
                } catch (NumberFormatException e) {
                    error = invalidQuantity + args.get(1) + " for command: " + tppos;
                    bError = true;
                }
            }
        } else error = tooManyArgs + tppos;

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("TELEPORT POSITION");
        return names;
    }

    @Override
    public String getTemplate() {
        return "TELEPORT POSITION {x} {y} {z}";
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
