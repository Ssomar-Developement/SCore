package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class XpBoost extends PlayerCommand {

    private static XpBoost instance;
    @Getter
    private final Map<UUID, List<Double>> activeBoosts;

    public XpBoost() {
        activeBoosts = new HashMap<>();
    }

    public static XpBoost getInstance() {
        if (instance == null) instance = new XpBoost();
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

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                //SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
                if (activeBoosts.containsKey(uuid)) {
                    if (activeBoosts.get(uuid).size() > 1) {
                        activeBoosts.get(uuid).remove(multiplier);
                    } else activeBoosts.remove(uuid);
                }
            }
        };
        SCore.schedulerHook.runTask(runnable3, time * 20L);
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
        names.add("XP_BOOST");
        names.add("XPBOOST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "XP_BOOST {multiplier} {timeinsecs}";
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
