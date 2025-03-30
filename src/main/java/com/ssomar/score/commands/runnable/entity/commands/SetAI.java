package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetAI extends EntityCommand {

    public SetAI() {
        CommandSetting value = new CommandSetting("value", 0, Boolean.class, true);
        List<CommandSetting> settings = getSettings();
        settings.add(value);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        boolean bol = (boolean) sCommandToExec.getSettingValue("value");

        //SsomarDev.testMsg("entity: "+entity.getType(), true);
        if (!entity.isDead() && entity instanceof LivingEntity) {
            LivingEntity receiver = (LivingEntity) entity;
            receiver.setAI(bol);
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_AI");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_AI value:true";
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
