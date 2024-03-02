package com.ssomar.score.commands.runnable.console;

import com.ssomar.score.commands.runnable.CommandManager;
import com.ssomar.score.commands.runnable.SCommand;

import java.util.ArrayList;
import java.util.List;

public class ConsoleCommandManager extends CommandManager<SCommand> {

    private static ConsoleCommandManager instance;

    public ConsoleCommandManager() {
        List<SCommand> commands = new ArrayList<>();
        setCommands(commands);
    }

    public static ConsoleCommandManager getInstance() {
        if (instance == null) instance = new ConsoleCommandManager();
        return instance;
    }
}
