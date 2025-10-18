package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class McMMOXpBoost extends PlayerCommand {

    private static McMMOXpBoost instance;
    @Getter
    private final Map<UUID, List<Double>> activeBoosts;

    public McMMOXpBoost() {
        activeBoosts = new HashMap<>();
    }

    public static McMMOXpBoost getInstance() {
        if (instance == null) instance = new McMMOXpBoost();
        return instance;
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        double multiplier = Double.valueOf(args.get(0));
        int time = Double.valueOf(args.get(1)).intValue();

        UUID uuid = receiver.getUniqueId();

        if (activeBoosts.containsKey(uuid)) {
            activeBoosts.get(uuid).add(multiplier);
        } else activeBoosts.put(uuid, new ArrayList<>(Collections.singletonList(multiplier)));

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (activeBoosts.containsKey(uuid)) {
                    if (activeBoosts.get(uuid).size() > 1) {
                        activeBoosts.get(uuid).remove(multiplier);
                    } else activeBoosts.remove(uuid);
                }
            }
        };
        SCore.schedulerHook.runTask(runnable, time * 20L);
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
        names.add("MCMMO_XP_BOOST");
        names.add("MCMMOXPBOOST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MCMMO_XP_BOOST {multiplier} {timeinsecs}";
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
