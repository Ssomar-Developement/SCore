package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* TELEPORT POSITION {x} {y} {z} */
public class TeleportPosition extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        if (args.size() == 3) {
            if (!entity.isDead())
                entity.teleport(new Location(entity.getWorld(), Double.valueOf(args.get(0)), Double.valueOf(args.get(1)), Double.valueOf(args.get(2))));
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        for (String arg : args) {
            ArgumentChecker ac = checkDouble(arg, isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }
        return Optional.empty();
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
