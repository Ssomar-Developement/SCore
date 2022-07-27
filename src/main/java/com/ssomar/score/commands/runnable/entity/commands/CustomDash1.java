package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* CUSTOMDASH1 {x} {y} {z} */
public class CustomDash1 extends EntityCommand {

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return com.ssomar.score.commands.runnable.player.commands.CustomDash1.staticVerif(args, isFinalVerification, getTemplate());
    }

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        double x = Double.parseDouble(args.get(0));
        double y = Double.parseDouble(args.get(1));
        double z = Double.parseDouble(args.get(2));

        Location loc = new Location(entity.getWorld(), x, y, z);
        com.ssomar.score.commands.runnable.player.commands.CustomDash1.pullEntityToLocation(entity, loc);
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
