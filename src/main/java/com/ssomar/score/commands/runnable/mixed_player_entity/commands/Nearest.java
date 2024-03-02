package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Nearest extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                double distance = Double.valueOf(args.get(0));

                //SsomarDev.testMsg("distance: " + distance, true);

                Player target = receiver.getWorld().getPlayers().stream()
                        .filter(p -> !p.equals(receiver) && p.getLocation().getWorld().equals(receiver.getLocation().getWorld()))
                        .min(Comparator.comparingDouble((p) -> p.getLocation().distanceSquared(receiver.getLocation())))
                        .orElse(null);

                if (target == null || target.getLocation().distance(receiver.getLocation()) > distance) {
                    return;
                }
                // SsomarDev.testMsg("target: " + target.getName(), true);

                if (target.hasMetadata("NPC") || target.equals(receiver)) return;

                List<Player> targets = new ArrayList<>();
                targets.add(target);

                CommmandThatRunsCommand.runPlayerCommands(targets, args.subList(1, args.size()), aInfo);

            }
        };
        SCore.schedulerHook.runTask(runnable, 0);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("NEAREST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "NEAREST {max distance} {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_PURPLE;
    }

}
