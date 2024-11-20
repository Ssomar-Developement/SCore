package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Oxygen extends PlayerCommand {

    public Oxygen() {
        CommandSetting time = new CommandSetting("time", 0, Integer.class, 200);
        List<CommandSetting> settings = getSettings();
        settings.add(time);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        int oxygen = (int) sCommandToExec.getSettingValue("time");
        int currentoxygen = receiver.getRemainingAir();
        int finaloxygen = 0;

        if(currentoxygen + oxygen < 0) finaloxygen = 0;
        else finaloxygen = currentoxygen + oxygen;

        //SsomarDev.testMsg("OXYGEN: "+finaloxygen, true);
        receiver.setRemainingAir(finaloxygen);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("OXYGEN");
        return names;
    }

    @Override
    public String getTemplate() {
        return "OXYGEN time:200";
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
