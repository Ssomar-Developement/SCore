package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CancelPickup extends PlayerCommand {

    public CancelPickup() {
        CommandSetting time = new CommandSetting("time", 0, Integer.class, 200);
        CommandSetting color = new CommandSetting("material", 1, Material.class, null);
        List<CommandSetting> settings = getSettings();
        settings.add(time);
        settings.add(color);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        int time = (int) sCommandToExec.getSettingValue("time");
        Material material = (Material) sCommandToExec.getSettingValue("material");

        if(material != null) CommandsHandler.getInstance().addStopPickup(receiver, time, material);
        else CommandsHandler.getInstance().addStopPickup(receiver, time);
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CANCEL_PICKUP");
        names.add("CANCELPICKUP");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CANCEL_PICKUP time:200 material:STONE";
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
