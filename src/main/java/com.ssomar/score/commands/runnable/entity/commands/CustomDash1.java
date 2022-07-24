package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;

/* CUSTOMDASH1 {x} {y} {z} */
public class CustomDash1 extends EntityCommand {

    private static void pullEntityToLocation(Entity e, Location loc) {
        Location entityLoc = e.getLocation();
        entityLoc.setY(entityLoc.getY() + 0.5D);
        e.teleport(entityLoc);
        double g = -0.08D;
        double d = loc.distance(entityLoc);
        double t = d;
        double v_x = (1.0D + 0.07D * t) * (loc.getX() - entityLoc.getX()) / t;
        double v_y = (1.0D + 0.03D * t) * (loc.getY() - entityLoc.getY()) / t - 0.5D * g * t;
        double v_z = (1.0D + 0.07D * t) * (loc.getZ() - entityLoc.getZ()) / t;
        Vector v = e.getVelocity();
        v.setX(v_x);
        v.setY(v_y);
        v.setZ(v_z);
        e.setVelocity(v);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String customDash1 = "CUSTOMDASH1 {x} {y} {z}";

        if (args.size() > 4) {
            error = tooManyArgs + customDash1;
            return error.isEmpty() ? Optional.empty() : Optional.of(error);
        } else if (args.size() < 3) {
            error = notEnoughArgs + customDash1;
        } else {
            if (!args.get(0).contains("%")) {
                try {
                    Double.valueOf(args.get(0));
                } catch (Exception e) {
                    error = invalidCoordinate + args.get(0) + " for command: " + customDash1;
                    return error.isEmpty() ? Optional.empty() : Optional.of(error);
                }
            }
            if (!args.get(1).contains("%")) {
                try {
                    Double.valueOf(args.get(1));
                } catch (Exception e) {
                    error = invalidCoordinate + args.get(1) + " for command: " + customDash1;
                    return error.isEmpty() ? Optional.empty() : Optional.of(error);
                }
            }
            if (!args.get(2).contains("%")) {
                try {
                    Double.valueOf(args.get(2));
                } catch (Exception e) {
                    error = invalidCoordinate + args.get(2) + " for command: " + customDash1;
                    return error.isEmpty() ? Optional.empty() : Optional.of(error);
                }
            }
        }

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        double x;
        double y;
        double z;

        if (args.size() >= 3) {
            try {
                x = Double.parseDouble(args.get(0));
                y = Double.parseDouble(args.get(1));
                z = Double.parseDouble(args.get(2));
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            Location loc = new Location(entity.getWorld(), x, y, z);
            pullEntityToLocation(entity, loc);
        } else return;
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CUSTOMDASH1");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CUSTOMDASH1 {x} {y} {z}";
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
