package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GravityDisable extends PlayerCommand {

    public GravityDisable() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        // Otherwise the player continue to fall if is falling
        receiver.setVelocity(receiver.getVelocity().setY(0));
        receiver.setGravity(false);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("GRAVITY_DISABLE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "GRAVITY_DISABLE";
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
