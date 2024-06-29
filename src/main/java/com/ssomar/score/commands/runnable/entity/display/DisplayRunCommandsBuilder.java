package com.ssomar.score.commands.runnable.entity.display;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.RunCommandsBuilder;

import java.util.List;

public class DisplayRunCommandsBuilder extends RunCommandsBuilder {

    public DisplayRunCommandsBuilder(List<String> commands, ActionInfo actionInfo) {
        super(commands, actionInfo);
    }

    @Override
    public RunCommand buildRunCommand(Integer delay, String command, ActionInfo aInfo) {
        return new DisplayRunCommand(command, delay, aInfo);
    }

}
