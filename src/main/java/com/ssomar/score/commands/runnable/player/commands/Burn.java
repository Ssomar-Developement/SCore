package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import com.ssomar.score.utils.NTools;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* BURN {timeinsecs} */
public class Burn extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        Optional<Double> intOptional = NTools.getDouble(args.get(0));
        int time = intOptional.get().intValue();

        if (SCore.hasWorldGuard) {
            if (WorldGuardAPI.isInPvpZone(receiver, receiver.getLocation())) {
                receiver.setFireTicks(20 * time);
            }
            /* setVisualFire appears in 1.17 */
            else if (SCore.is1v17Plus()) {
                receiver.setVisualFire(true);

                BukkitRunnable runnable = new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (receiver.isOnline()) receiver.setVisualFire(false);
                    }
                };
                runnable.runTaskLater(SCore.plugin, time);
            }
        } else receiver.setFireTicks(20 * time);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("BURN");
        return names;
    }

    @Override
    public String getTemplate() {
        return "BURN {timeinsecs}";
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
