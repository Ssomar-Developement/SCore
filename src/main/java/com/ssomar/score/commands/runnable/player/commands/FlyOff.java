package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.fly.FlyManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* FLY OFF */
@SuppressWarnings("deprecation")
public class FlyOff extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        boolean teleport = true;
        if (args.size() >= 1) {
            teleport = Boolean.parseBoolean(args.get(0));
        }

        if (teleport) {
            if (!receiver.isOnGround()) {
                Location playerLocation = receiver.getLocation();
                boolean isVoid = false;
                while (playerLocation.getBlock().isEmpty()) {
                    if (playerLocation.getY() <= 1) {
                        isVoid = true;
                        break;
                    }
                    playerLocation.subtract(0, 1, 0);
                }
                if (!isVoid) {
                    playerLocation.add(0, 1, 0);
                    receiver.teleport(playerLocation);
                }
            }
        }
        receiver.setAllowFlight(false);
        receiver.setFlying(false);
        FlyManager.getInstance().removePlayerWithFly(p);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        if (args.size() >= 1) {
            ArgumentChecker ac = checkBoolean(args.get(0), isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FLY OFF");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FLY OFF [teleportOnTheGround true or false]";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.BLUE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.AQUA;
    }
}
