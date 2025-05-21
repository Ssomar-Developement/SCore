package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.particles.commands.XParticle;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Dash extends MixedCommand {

    public Dash() {
        CommandSetting power = new CommandSetting("power", -1, Double.class, 1);
        CommandSetting pitch = new CommandSetting("pitch", -1, Double.class, -1);
        CommandSetting yaw = new CommandSetting("yaw", -1, Double.class, -1);
        List<CommandSetting> settings = getSettings();
        settings.add(power);
        settings.add(pitch);
        settings.add(yaw);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity receiver, SCommandToExec sCommandToExec) {
        double power = (double) sCommandToExec.getSettingValue("power");
        double pitch = (double) sCommandToExec.getSettingValue("pitch");
        double yaw = (double) sCommandToExec.getSettingValue("yaw");

        Location loc = receiver.getLocation();

        if(pitch == -1) pitch = loc.getPitch();
        if(yaw == -1) yaw = loc.getYaw();

        Vector direction = XParticle.calculDirection(yaw, pitch).multiply(power);
        receiver.setVelocity(direction);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DASH");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DASH pitch:-1 yaw:-1 power:2";
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
