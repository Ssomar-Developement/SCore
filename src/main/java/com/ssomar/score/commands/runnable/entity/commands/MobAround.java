package com.ssomar.score.commands.runnable.entity.commands;


import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.ssomar.score.commands.runnable.player.commands.MobAround.mobAroundExecution;

public class MobAround extends EntityCommand {

    public MobAround() {
        CommandSetting distance = new CommandSetting("distance", 0, Double.class, 3d);
        CommandSetting throughBlocks = new CommandSetting("throughBlocks", -1, Boolean.class, true);
        CommandSetting safeDistance = new CommandSetting("safeDistance", -1, Double.class, 0d);
        CommandSetting x = new CommandSetting("x", -1, Double.class, 0d);
        CommandSetting y = new CommandSetting("y", -1, Double.class, 0d);
        CommandSetting z = new CommandSetting("z", -1, Double.class, 0d);
        CommandSetting world = new CommandSetting("world", -1, String.class, 0d);
        List<CommandSetting> settings = getSettings();
        settings.add(distance);
        settings.add(throughBlocks);
        settings.add(safeDistance);
        settings.add(x);
        settings.add(y);
        settings.add(z);
        settings.add(world);
        setNewSettingsMode(true);
        setCanExecuteCommands(true);
    }

    @Override
    public void run(Player p, Entity receiver, SCommandToExec sCommandToExec) {
        mobAroundExecution(null, p, receiver, false, sCommandToExec);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MOB_AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MOB_AROUND {distance} {Your commands here}";
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