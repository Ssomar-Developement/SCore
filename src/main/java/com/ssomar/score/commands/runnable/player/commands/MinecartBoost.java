package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* BURN {timeinsecs} */
public class MinecartBoost extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        Entity entity;
        double boost = Double.parseDouble(args.get(0));

        if ((entity = receiver.getVehicle()) != null && entity instanceof Minecart) {
            Minecart minecart = (Minecart) entity;
            Location minecartLoc = minecart.getLocation();
            Block potentialRails = minecartLoc.getBlock();
            if (potentialRails.getType().toString().contains("RAIL")) {
                minecart.setVelocity(receiver.getEyeLocation().getDirection().multiply(boost));
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MINECART_BOOST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MINECART_BOOST {boost (number)}";
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
