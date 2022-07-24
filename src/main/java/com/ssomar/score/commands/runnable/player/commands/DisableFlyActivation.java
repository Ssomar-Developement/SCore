package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/* BURN {timeinsecs} */
public class DisableFlyActivation extends PlayerCommand {

    private static DisableFlyActivation instance;
    @Getter
    private Map<UUID, Integer> activeDisabled;

    public DisableFlyActivation() {
        activeDisabled = new HashMap<>();
    }

    public static DisableFlyActivation getInstance() {
        if (instance == null) instance = new DisableFlyActivation();
        return instance;
    }

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        int time = Double.valueOf(args.get(0)).intValue();

        if (activeDisabled.containsKey(receiver.getUniqueId())) {
            activeDisabled.put(receiver.getUniqueId(), activeDisabled.get(receiver.getUniqueId()) + 1);
        } else activeDisabled.put(receiver.getUniqueId(), 1);

        BukkitRunnable runnable3 = new BukkitRunnable() {
            @Override
            public void run() {
                //SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
                if (activeDisabled.containsKey(receiver.getUniqueId())) {
                    if (activeDisabled.get(receiver.getUniqueId()) > 1) {
                        activeDisabled.put(receiver.getUniqueId(), activeDisabled.get(receiver.getUniqueId()) - 1);
                    } else activeDisabled.remove(receiver.getUniqueId());
                }
            }
        };
        runnable3.runTaskLater(SCore.plugin, time * 20);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DISABLE_FLY_ACTIVATION");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DISABLE_FLY_ACTIVATION {timeinsecs}";
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
