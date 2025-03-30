package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/* KILL */
public class Kill extends EntityCommand {

    public Kill() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        if (!entity.isDead()) entity.remove();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("KILL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "KILL";
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
