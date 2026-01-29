package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetPlayerTime extends PlayerCommand {

    public SetPlayerTime() {
        CommandSetting time = new CommandSetting("time", 0, Integer.class, 6000);
        CommandSetting relative = new CommandSetting("relative", 1, Boolean.class, false);
        List<CommandSetting> settings = getSettings();
        settings.add(time);
        settings.add(relative);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        int time = (int) sCommandToExec.getSettingValue("time");
        boolean relative = (boolean) sCommandToExec.getSettingValue("relative");
        if (time == -1) {
            receiver.resetPlayerTime();
        } else {
            receiver.setPlayerTime(time, relative);
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_PLAYER_TIME");
        names.add("SETPLAYERTIME");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_PLAYER_TIME time:6000 relative:false";
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
