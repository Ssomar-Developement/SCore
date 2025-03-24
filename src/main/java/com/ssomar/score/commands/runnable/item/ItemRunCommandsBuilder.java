package com.ssomar.score.commands.runnable.item;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.RunCommandsBuilder;

import java.util.List;

public class ItemRunCommandsBuilder extends RunCommandsBuilder {

    public ItemRunCommandsBuilder(List<String> commands, ActionInfo actionInfo) {
        super(commands, actionInfo);
    }

    @Override
    public RunCommand buildRunCommand(Integer delay, String command, ActionInfo aInfo) {
        return new ItemRunCommand(command, delay, aInfo);
    }

}
