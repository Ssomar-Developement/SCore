package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;


public class DisableFlyActivation extends PlayerCommand {

    private static DisableFlyActivation instance;
    @Getter
    private final Map<UUID, Integer> activeDisabled;

    public DisableFlyActivation() {
        activeDisabled = new HashMap<>();

        CommandSetting time = new CommandSetting("time", 0, Integer.class, 10);
        List<CommandSetting> settings = getSettings();
        settings.add(time);
        setNewSettingsMode(true);
    }

    public static DisableFlyActivation getInstance() {
        if (instance == null) instance = new DisableFlyActivation();
        return instance;
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        int time = (int) sCommandToExec.getSettingValue("time");

        UUID receiverUUID = receiver.getUniqueId();

        if (activeDisabled.containsKey(receiverUUID)) {
            activeDisabled.put(receiverUUID, activeDisabled.get(receiverUUID) + 1);
        } else activeDisabled.put(receiverUUID, 1);


        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                //SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
                if (activeDisabled.containsKey(receiverUUID)) {
                    if (activeDisabled.get(receiverUUID) > 1) {
                        activeDisabled.put(receiverUUID, activeDisabled.get(receiver.getUniqueId()) - 1);
                    } else activeDisabled.remove(receiverUUID);
                }
            }
        };
        SCore.schedulerHook.runTask(runnable3, time * 20L);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DISABLE_FLY_ACTIVATION");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DISABLE_FLY_ACTIVATION time:10";
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
