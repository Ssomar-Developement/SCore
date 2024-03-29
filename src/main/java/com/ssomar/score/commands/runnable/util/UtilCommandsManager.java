package com.ssomar.score.commands.runnable.util;

import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.util.commands.*;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilCommandsManager {

    private static UtilCommandsManager instance;

    private List<SCommand> commands;

    public UtilCommandsManager() {
        List<SCommand> commands = new ArrayList<>();
        commands.add(new Delay());
        commands.add(new DelayTick());
        commands.add(new Nothing());
        commands.add(new RandomRun());
        commands.add(new RandomEnd());
        this.commands = commands;
    }

    public static UtilCommandsManager getInstance() {
        if (instance == null) instance = new UtilCommandsManager();
        return instance;
    }

    public List<SCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<SCommand> commands) {
        this.commands = commands;
    }

    public Map<String, String> getCommandsDisplay() {
        Map<String, String> result = new HashMap<>();
        for (SCommand c : this.commands) {

            ChatColor extra = c.getExtraColor();
            if (extra == null) extra = ChatColor.GOLD;

            ChatColor color = c.getColor();
            if (color == null) color = ChatColor.YELLOW;

            result.put(extra + "[" + color + "&l" + c.getNames().get(0) + extra + "]", c.getTemplate());
        }
        return result;
    }

}
