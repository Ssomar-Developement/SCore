package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Particle extends BlockCommand {

    public Particle() {
        CommandSetting particle = new CommandSetting("particle", 0, String.class, "FLAME");
        CommandSetting quantity = new CommandSetting("quantity", 1, Integer.class, 5);
        CommandSetting offset = new CommandSetting("offset", 2, Double.class, 2.0);
        CommandSetting speed = new CommandSetting("speed", 3, Double.class, 1.0);
        List<CommandSetting> settings = getSettings();
        settings.add(particle);
        settings.add(quantity);
        settings.add(offset);
        settings.add(speed);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Block block, SCommandToExec sCommandToExec) {
        String particle = (String) sCommandToExec.getSettingValue("particle");
        int quantity = (int) sCommandToExec.getSettingValue("quantity");
        double offset = (double) sCommandToExec.getSettingValue("offset");
        double speed = (double) sCommandToExec.getSettingValue("speed");
        try {
            block.getWorld().spawnParticle(org.bukkit.Particle.valueOf(particle.toUpperCase()),
                    block.getLocation(),
                    quantity,
                    offset,
                    offset,
                    offset,
                    speed, null);
        } catch (Exception ignored) {}
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("PARTICLE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "PARTICLE particle:FLAME quantity:5 offset:2.0 speed:1.0";
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
