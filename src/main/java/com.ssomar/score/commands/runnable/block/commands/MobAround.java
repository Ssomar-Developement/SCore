package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.ssomar.executableitems.events.optimize.OptimizedEventsHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.commands.runnable.entity.EntityRunCommandsBuilder;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.jetbrains.annotations.NotNull;

import static com.ssomar.score.commands.runnable.player.commands.MobAround.mobAroundExecution;

/* MOB_AROUND {distance} {Your commands here} */
public class MobAround extends BlockCommand {

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String around = "MOB_AROUND {distance} {Your commands here}";
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
        names.add("MOB_AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MOB_AROUND {distance} {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

    @Override
    public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        mobAroundExecution(block.getLocation(), null, true, args, aInfo);
    }
}
