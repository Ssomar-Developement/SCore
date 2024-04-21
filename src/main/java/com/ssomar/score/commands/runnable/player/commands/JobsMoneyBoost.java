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

public class JobsMoneyBoost extends PlayerCommand {

    private static JobsMoneyBoost instance;
    @Getter
    private final Map<UUID, List<Double>> activeBoosts;

    public JobsMoneyBoost() {
        activeBoosts = new HashMap<>();
    }

    public static JobsMoneyBoost getInstance() {
        if (instance == null) instance = new JobsMoneyBoost();
        return instance;
    }

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        double multiplier = Double.valueOf(args.get(0));
        int time = Double.valueOf(args.get(1)).intValue();

        if (activeBoosts.containsKey(receiver.getUniqueId())) {
            activeBoosts.get(receiver.getUniqueId()).add(multiplier);
        } else activeBoosts.put(receiver.getUniqueId(), new ArrayList<>(Collections.singletonList(multiplier)));

        BukkitRunnable runnable3 = new BukkitRunnable() {
            @Override
            public void run() {
                //SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
                if (activeBoosts.containsKey(receiver.getUniqueId())) {
                    if (activeBoosts.get(receiver.getUniqueId()).size() > 1) {
                        activeBoosts.get(receiver.getUniqueId()).remove(multiplier);
                    } else activeBoosts.remove(receiver.getUniqueId());
                }
            }
        };
        runnable3.runTaskLater(SCore.plugin, time * 20L);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkInteger(args.get(1), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("JOBS_MONEY_BOOST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "JOBS_MONEY_BOOST {multiplier} {timeinsecs}";
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
