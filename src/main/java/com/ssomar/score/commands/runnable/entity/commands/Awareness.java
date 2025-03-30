package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Awareness extends EntityCommand {

    public Awareness() {
        CommandSetting value = new CommandSetting("value", 0, Boolean.class, true);
        List<CommandSetting> settings = getSettings();
        settings.add(value);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        boolean bol = (boolean) sCommandToExec.getSettingValue("value");
        if(entity instanceof Mob){
            Mob mob = (Mob) entity;
            mob.setAware(bol);
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("AWARENESS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "AWARENESS value:true";
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
