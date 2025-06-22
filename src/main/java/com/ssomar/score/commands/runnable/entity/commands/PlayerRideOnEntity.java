package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerRideOnEntity extends EntityCommand {

    public PlayerRideOnEntity() {
        CommandSetting value = new CommandSetting("control", -1, Boolean.class, true);
        CommandSetting speed = new CommandSetting("speed", -1, Double.class, 1.0);
        List<CommandSetting> settings = getSettings();
        settings.add(value);
        settings.add(speed);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        boolean control = (boolean) sCommandToExec.getSettingValue("control");
        double speed = (double) sCommandToExec.getSettingValue("speed");

        if (p != null && !entity.isDead() && p.isOnline() && !p.isDead()) {
            entity.addPassenger(p);
            if(SCore.is1v21v4Plus() && control) PlayerRideOnEntityManager.getInstance().addRider(p.getUniqueId(), speed);
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
        return "PLAYER_RIDE_ON_ENTITY control:true speed:1.0";
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
