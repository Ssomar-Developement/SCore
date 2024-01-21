package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/* AROUND {distance} {true or false} {Your commands here} */
public class Around extends BlockCommand {

    private final static Boolean DEBUG = false;

    public Around() {
        setCanExecuteCommands(true);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String around = "AROUND {distance} [affectThePlayerThatActivesTheActivator true or false] {Your commands here}";

        if (args.size() < 2) error = notEnoughArgs + around;
        else if (args.size() > 2) {
            try {
                Double.valueOf(args.get(0));
            } catch (NumberFormatException e) {
                error = invalidDistance + args.get(0) + " for command: " + around;
            }
        }

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "AROUND {distance} [affectThePlayerThatActivesTheActivator true or false] {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_PURPLE;
    }

    @Override
    public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    double distance = Double.parseDouble(args.get(0));
                    boolean affectThePlayerThatActivesTheActivator = true;
                    if (args.get(1).equalsIgnoreCase("false")) affectThePlayerThatActivesTheActivator = false;

                    List<Player> targets = new ArrayList<>();
                    for (Entity e : block.getWorld().getNearbyEntities(block.getLocation().add(0.5, 0.5, 0.5), distance, distance, distance)) {
                        if (e instanceof Player) {
                            Player target = (Player) e;
                            if (target.hasMetadata("NPC") || (!affectThePlayerThatActivesTheActivator && (p != null && p.equals(target))))
                                continue;
                            targets.add(target);

                        }
                    }
                    CommmandThatRunsCommand.runPlayerCommands(targets, args.subList(1, args.size()), aInfo);
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }
        };
        SCore.schedulerHook.runTask(runnable, 0);
    }
}
