package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssomar.score.commands.runnable.player.commands.Around.aroundExecution;

public class Around extends EntityCommand {

    public Around() {
        setCanExecuteCommands(true);
        CommandSetting distance = new CommandSetting("distance",0, Double.class, 3d);
        CommandSetting throughBlocks = new CommandSetting("throughBlocks", -1, Boolean.class, true);
        CommandSetting safeDistance = new CommandSetting("safeDistance", -1, Double.class, 0d);
        CommandSetting x = new CommandSetting("x", -1, Double.class, 0d);
        CommandSetting y = new CommandSetting("y", -1, Double.class, 0d);
        CommandSetting z = new CommandSetting("z", -1, Double.class, 0d);
        CommandSetting world = new CommandSetting("world", -1, String.class, "");     
        CommandSetting targetMobs = new CommandSetting("targetMobs", -1, Boolean.class, false, false);    
        CommandSetting targetSelf = new CommandSetting("targetSelf", -1, Boolean.class, true, true);      
        CommandSetting targetNPC = new CommandSetting("targetNPC", -1, Boolean.class, false, false);     
        List<CommandSetting> settings = getSettings();
        settings.add(distance);
        settings.add(throughBlocks);
        settings.add(safeDistance);
        settings.add(x);
        settings.add(y);
        settings.add(z);
        settings.add(world);    
        settings.add(targetMobs);
        settings.add(targetSelf);
        settings.add(targetNPC);               
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity receiver, SCommandToExec sCommandToExec) {
        aroundExecution(receiver, sCommandToExec, false);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "AROUND {distance} {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_PURPLE;
    }
}
