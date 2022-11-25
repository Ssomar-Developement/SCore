package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FrontDash extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        double amount = Double.parseDouble(args.get(0));
        double customY = 0;
        if (args.size() > 1) customY = Double.parseDouble(args.get(1));

        Location pLoc = receiver.getLocation();
        if (SCore.is1v11Less() || !receiver.isGliding()) pLoc.setPitch(0);
        Vector v = pLoc.getDirection();
        v.multiply(amount);
        if (customY != 0) {
            Vector vec = new Vector();
            vec.setY(customY);
            v.add(vec);
        }
        receiver.setVelocity(v);

    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        if (args.size() > 1) {
            ArgumentChecker ac2 = checkDouble(args.get(1), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FRONTDASH");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FRONTDASH {number} [custom y]";
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
