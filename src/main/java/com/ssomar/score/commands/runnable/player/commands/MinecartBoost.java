package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MinecartBoost extends PlayerCommand {

    public MinecartBoost() {
        CommandSetting boost = new CommandSetting("boost", 0, Double.class, 2.0);
        List<CommandSetting> settings = getSettings();
        settings.add(boost);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        Entity entity;
        double boost = (double) sCommandToExec.getSettingValue("boost");

        if ((entity = receiver.getVehicle()) != null && entity instanceof Minecart) {
            Minecart minecart = (Minecart) entity;
            Location minecartLoc = minecart.getLocation();
            Block potentialRails = minecartLoc.getBlock();
            if (potentialRails.getType().toString().contains("RAIL")) {
                minecart.setVelocity(receiver.getEyeLocation().getDirection().multiply(boost));
            }
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MINECART_BOOST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MINECART_BOOST boost:2.0";
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
