package com.ssomar.score.commands.runnable.entity.commands;


import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssomar.score.commands.runnable.player.commands.MobAround.mobAroundExecution;

public class MobAround extends EntityCommand {

    public MobAround() {
        setCanExecuteCommands(true);
    }

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        mobAroundExecution(receiver.getLocation(), p, receiver, true, args, aInfo);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return com.ssomar.score.commands.runnable.player.commands.MobAround.staticVerify(args, isFinalVerification, getTemplate());
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MOB_AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MOB_AROUND {distance} {Your commands here}";
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