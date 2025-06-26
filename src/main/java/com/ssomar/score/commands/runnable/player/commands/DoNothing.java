package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DoNothing extends PlayerCommand {

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DO_NOTHING");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DO_NOTHING {text/placeholder/anything}";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

    @Override
    public void run(@Nullable Player launcher, Player receiver, SCommandToExec sCommandToExec) {
    }
}
