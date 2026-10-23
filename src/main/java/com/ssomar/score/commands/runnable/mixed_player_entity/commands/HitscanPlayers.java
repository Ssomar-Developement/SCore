package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HitscanPlayers extends MixedCommand {


    public HitscanPlayers() {
        CommandSetting range = new CommandSetting("range", -1, Double.class, 5d);
        CommandSetting radius = new CommandSetting("radius", -1, Double.class, 0d);
        CommandSetting pitch = new CommandSetting("pitch", -1, Double.class, 0d);
        CommandSetting yaw = new CommandSetting("yaw", -1, Double.class, 0d);
        CommandSetting leftRightShift = new CommandSetting("leftRightShift", -1, Double.class, 0d);
        CommandSetting yShift = new CommandSetting("yShift", -1, Double.class, 0d);
        CommandSetting throughBlocks = new CommandSetting("throughBlocks", -1, Boolean.class, true);
        CommandSetting throughEntities = new CommandSetting("throughEntities", -1, Boolean.class, true);
        CommandSetting limit = new CommandSetting("limit", -1, Integer.class, -1);
        CommandSetting sort = new CommandSetting("sort", -1, String.class, "NEAREST");
        CommandSetting regionCheck = new CommandSetting("regionCheck", -1, Boolean.class, false);
        List<CommandSetting> settings = getSettings();
        settings.add(range);
        settings.add(radius);
        settings.add(pitch);
        settings.add(yaw);
        settings.add(leftRightShift);
        settings.add(yShift);
        settings.add(throughEntities);
        settings.add(throughBlocks);
        settings.add(limit);
        settings.add(sort);
        settings.add(regionCheck);
        setNewSettingsMode(true);
        setCanExecuteCommands(true);
    }

    @Override
    public void run(Player p, Entity receiver, SCommandToExec sCommandToExec) {
        List<Entity> entities = HitscanEntities.runHitscan(receiver, sCommandToExec, true, p);
        List<Player> players = new ArrayList<>();
        for (Entity entity : entities){
            if(entity instanceof Player) players.add((Player) entity);
        }
        CommmandThatRunsCommand.runPlayerCommands(players, sCommandToExec.getOtherArgs(), sCommandToExec.getActionInfo());

    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {


        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("HITSCAN_PLAYERS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "HITSCAN_PLAYERS range:5 radius:0 pitch:0 yaw:0 leftRightShift:0 yShift:0 throughBlocks:true throughEntities:true COMMANDS HERE";
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

