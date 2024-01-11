package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import com.ssomar.score.nofalldamage.NoFallDamageManager;
import com.ssomar.score.utils.Couple;
import com.ssomar.score.utils.scheduler.ScheduledTask;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/* CUSTOMDASH1 {x} {y} {z} */
public class CustomDash1 extends MixedCommand {

    public static void pullEntityToLocation(Entity e, Location loc) {
        Location entityLoc = e.getLocation();
        Vector vec = e.getVelocity().clone().setY(entityLoc.getY() + 0.5D);
        e.setVelocity(vec);
        double g = -0.08D;
        double v_x = (1.0D + 0.07D * loc.distance(entityLoc)) * (loc.getX() - entityLoc.getX()) / loc.distance(entityLoc);
        double v_y = (1.0D + 0.03D * loc.distance(entityLoc)) * (loc.getY() - entityLoc.getY()) / loc.distance(entityLoc) - 0.5D * g * loc.distance(entityLoc);
        double v_z = (1.0D + 0.07D * loc.distance(entityLoc)) * (loc.getZ() - entityLoc.getZ()) / loc.distance(entityLoc);
        Vector v = e.getVelocity();
        v.setX(v_x);
        v.setY(v_y);
        v.setZ(v_z);
        e.setVelocity(v);
    }

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
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
                    NoFallDamageManager.getInstance().removeNoFallDamage(receiver, uuid);
                }
            };
            ScheduledTask task = SCore.schedulerHook.runTask(runnable, 300);
            NoFallDamageManager.getInstance().addNoFallDamage(receiver, new Couple<>(uuid, task));
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return staticVerif(args, isFinalVerification, getTemplate());
    }

    public static Optional<String> staticVerif(List<String> args, boolean isFinalVerification, String template){
        if (args.size() < 3) return Optional.of(notEnoughArgs + template);

        for (int i = 0; i < 3; i++) {
            ArgumentChecker ac = checkDouble(args.get(i), isFinalVerification, template);
            if (!ac.isValid()) return Optional.of(ac.getError());
        }

        if (args.size() >= 4) {
            String value = args.get(3);
            ArgumentChecker ac = checkBoolean(value, isFinalVerification, template);
            if (!ac.isValid()) return Optional.of(ac.getError());
        }

        return Optional.empty();
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
