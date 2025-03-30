package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Hide extends EntityCommand {

    public Hide() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        if(p != null && entity instanceof Mob && !entity.isDead()){
            Mob mob = (Mob) entity;
            try {
                p.hideEntity(SCore.plugin, mob);
            } catch (Exception ignore) {}
        }
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("HIDE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "HIDE";
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
