package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.events.PlayerCustomLaunchEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* LAUNCHENTITY {entityType} */
public class LaunchEntity extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        Location loc = receiver.getEyeLocation();
        //loc.setY(loc.getY()-1);
        EntityType entityType = EntityType.PIG;
        double speed = 1;
        double rotation = 0;

        if (args.size() >= 1) {
            try {
                entityType = EntityType.valueOf(args.get(0).toUpperCase());
            } catch (Exception ignored) {

            }
        }
        if (args.size() >= 2) {
            try {
                speed = Double.parseDouble(args.get(1));
            } catch (Exception ignored) {
            }
        }

        if (args.size() >= 3) {
            try {
                rotation = Double.parseDouble(args.get(2));
                rotation = rotation * Math.PI / 180;
            } catch (Exception ignored) {
            }
        }

        Entity entity = receiver.getWorld().spawnEntity(loc, entityType);
        Vector v = receiver.getEyeLocation().getDirection();
        v.multiply(speed);
        if (!SCore.is1v13Less()) v.rotateAroundY(rotation);
        entity.setVelocity(v);

        PlayerCustomLaunchEntityEvent playerCustomLaunchProjectileEvent = new PlayerCustomLaunchEntityEvent(receiver, entity);
        Bukkit.getServer().getPluginManager().callEvent(playerCustomLaunchProjectileEvent);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String launch = "LAUNCHENTITY {entityType} {speed} [angle rotation y]";
        if (args.size() < 1) error = notEnoughArgs + launch;

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("LAUNCHENTITY");
        return names;
    }

    @Override
    public String getTemplate() {
        return "LAUNCHENTITY {entityType} {speed} [angle rotation y]";
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
