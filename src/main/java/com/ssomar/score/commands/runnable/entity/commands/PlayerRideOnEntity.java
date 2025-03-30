package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerRideOnEntity extends EntityCommand {

    public PlayerRideOnEntity() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        if (p != null && !entity.isDead() && p.isOnline() && !p.isDead()) {
            entity.addPassenger(p);
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("PLAYER_RIDE_ON_ENTITY");
        return names;
    }

    @Override
    public String getTemplate() {
        return "PLAYER_RIDE_ON_ENTITY";
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
