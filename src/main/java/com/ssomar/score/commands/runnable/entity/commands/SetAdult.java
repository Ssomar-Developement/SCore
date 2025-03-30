package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/* SETADULT */
public class SetAdult extends EntityCommand {

    public SetAdult() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        if (!entity.isDead() && entity instanceof Ageable) ((Ageable) entity).setAdult();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ADULT");
        names.add("SETADULT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ADULT";
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
