package com.ssomar.score.commands.runnable;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.block.commands.SilkSpawner;

import java.util.ArrayList;
import java.util.List;


public class CommandsExecutor {

    public static void runCommands(RunCommandsBuilder builder) {
        List<RunCommand> runCommands = new ArrayList<>();
        for (int i : builder.getFinalCommands().keySet()) {
            runCommands.addAll(builder.getFinalCommands().get(i));
        }
        for (RunCommand runCommand : runCommands) {
            runCommand.run();
        }
    }
}
