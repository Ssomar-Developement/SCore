package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ssomar.score.commands.runnable.ArgumentChecker;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.nofalldamage.NoFallDamageManager;

/* JUMP {amount} */
public class Jump extends PlayerCommand {


    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        double jump = Double.parseDouble(args.get(0));

        Vector v = receiver.getVelocity();
        v.setX(0);
        v.setY(jump);
        v.setZ(0);
        receiver.setVelocity(v);

        /* NO FALL DAMAGE PART */
        NoFallDamageManager.getInstance().addNoFallDamage(receiver);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        if(args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("JUMP");
        return names;
    }

    @Override
    public String getTemplate() {
        return "JUMP {number (max 5)}";
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