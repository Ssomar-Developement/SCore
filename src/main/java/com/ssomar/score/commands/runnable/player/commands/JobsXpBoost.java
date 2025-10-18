package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class JobsXpBoost extends PlayerCommand {

    private static JobsXpBoost instance;
    @Getter
    private final Map<UUID, List<Double>> activeBoosts;

    public JobsXpBoost() {
        activeBoosts = new HashMap<>();

        CommandSetting multiplier = new CommandSetting("multiplier", 0, Double.class, 2.0);
        CommandSetting time = new CommandSetting("time", 1, Integer.class, 10);
        List<CommandSetting> settings = getSettings();
        settings.add(multiplier);
        settings.add(time);
        setNewSettingsMode(true);
    }

    public static JobsXpBoost getInstance() {
        if (instance == null) instance = new JobsXpBoost();
        return instance;
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {

        double multiplier = (double) sCommandToExec.getSettingValue("multiplier");
        int time = (int) sCommandToExec.getSettingValue("time");

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
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("JOBS_XP_BOOST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "JOBS_XP_BOOST multiplier:2.0 time:10";
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
