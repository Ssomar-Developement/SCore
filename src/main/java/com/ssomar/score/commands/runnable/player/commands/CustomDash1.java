package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.nofalldamage.NoFallDamageManager;
import com.ssomar.score.utils.Couple;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/* CUSTOMDASH1 {x} {y} {z} */
public class CustomDash1 extends PlayerCommand {

    private static void pullEntityToLocation(Entity e, Location loc) {
        Location entityLoc = e.getLocation();
        Vector vec = e.getVelocity().clone().setY(entityLoc.getY() + 0.5D);
        e.setVelocity(vec);
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
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        boolean fallDamage = false;
        if (args.size() >= 4) {
            fallDamage = Boolean.parseBoolean(args.get(3));
        }

        double x = Double.parseDouble(args.get(0));
        double y = Double.parseDouble(args.get(1));
        double z = Double.parseDouble(args.get(2));


        Location loc = new Location(receiver.getWorld(), x, y, z);
        pullEntityToLocation(receiver, loc);


        UUID uuid = UUID.randomUUID();

        if (!fallDamage) {
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    NoFallDamageManager.getInstance().removeNoFallDamage(p, uuid);
                }
            };
            BukkitTask task = runnable.runTaskLater(SCore.plugin, 300);

            NoFallDamageManager.getInstance().addNoFallDamage(receiver, new Couple<>(uuid, task));
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        if (args.size() < 3) return Optional.of(notEnoughArgs + getTemplate());

        for (int i = 0; i < 3; i++) {
            ArgumentChecker ac = checkDouble(args.get(i), isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }

        if (args.size() >= 4) {
            String value = args.get(3);
            ArgumentChecker ac = checkBoolean(value, isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CUSTOMDASH1");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CUSTOMDASH1 {x} {y} {z} [fallDamage]";
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
