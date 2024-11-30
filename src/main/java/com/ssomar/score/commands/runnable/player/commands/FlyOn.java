package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.fly.FlyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlyOn extends PlayerCommand {

    public FlyOn() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        receiver.setAllowFlight(true);
        FlyManager.getInstance().addPlayerWithFly(p);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FLY_ON");
        names.add("FLY ON");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FLY_ON";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.BLUE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.AQUA;
    }
}
