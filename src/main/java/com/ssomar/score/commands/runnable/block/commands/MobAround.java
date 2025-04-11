package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.ssomar.score.commands.runnable.mixed_player_entity.commands.MobAround.mobAroundExecution;

/* MOB_AROUND {distance} {Your commands here} */
public class MobAround extends BlockCommand {

    public MobAround() {

        CommandSetting distance = new CommandSetting("distance", 0, Double.class, 3d);
        CommandSetting displayMsgIfNoPlayer = new CommandSetting("displayMsgIfNoEntity", -1, Boolean.class, true);
        CommandSetting throughBlocks = new CommandSetting("throughBlocks", -1, Boolean.class, true);
        CommandSetting safeDistance = new CommandSetting("safeDistance", -1, Double.class, 0d);
        List<CommandSetting> settings = getSettings();
        settings.add(distance);
        settings.add(displayMsgIfNoPlayer);
        settings.add(throughBlocks);
        settings.add(safeDistance);
        setNewSettingsMode(true);
        setCanExecuteCommands(true);
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

    @Override
    public void run(Player p, @NotNull Block block, SCommandToExec sCommandToExec) {
        mobAroundExecution(block.getLocation(), p, null, true, sCommandToExec);
    }
}
