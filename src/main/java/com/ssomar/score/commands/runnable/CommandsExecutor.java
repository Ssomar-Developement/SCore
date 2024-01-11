package com.ssomar.score.commands.runnable;

import java.util.ArrayList;
import java.util.List;


public class CommandsExecutor {

    public static void runCommands(RunCommandsBuilder builder) {
        List<RunCommand> runCommands = new ArrayList<>();
        for (int i : builder.getFinalCommands().keySet()) {
            runCommands.addAll(builder.getFinalCommands().get(i));
        }
        for (RunCommand runCommand : runCommands) {
            //SsomarDev.testMsg("RUN COMMAND: >>>" + runCommand.getBrutCommand(), true);
            runCommand.run();
        }
    }
}
