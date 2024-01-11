package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* CUSTOMDASH2 {x} {y} {z} {strength}*/
public class CustomDash2 extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        double x = Double.parseDouble(args.get(0));
        double y = Double.parseDouble(args.get(1));
        double z = Double.parseDouble(args.get(2));


        float strength = Float.parseFloat(args.get(3));
        Location target = new Location(receiver.getWorld(), x, y, z);

        Vector dashDir = receiver.getLocation().toVector().subtract(target.toVector()).normalize().multiply(strength);
        receiver.setVelocity(dashDir);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 4) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac1 = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac1.isValid()) return Optional.of(ac1.getError());

        ArgumentChecker ac2 = checkDouble(args.get(1), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        ArgumentChecker ac3 = checkDouble(args.get(2), isFinalVerification, getTemplate());
        if (!ac3.isValid()) return Optional.of(ac3.getError());

        ArgumentChecker ac4 = checkDouble(args.get(3), isFinalVerification, getTemplate());
        if (!ac4.isValid()) return Optional.of(ac4.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CUSTOMDASH2");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CUSTOMDASH2 {x} {y} {z} {strength}";
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
