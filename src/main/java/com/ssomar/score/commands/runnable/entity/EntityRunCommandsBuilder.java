package com.ssomar.score.commands.runnable.entity;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.RunCommandsBuilder;

import java.util.List;

public class EntityRunCommandsBuilder extends RunCommandsBuilder {

    public EntityRunCommandsBuilder(List<String> commands, ActionInfo actionInfo) {
        super(commands, actionInfo);
    }

    @Override
    public RunCommand buildRunCommand(Integer delay, String command, ActionInfo aInfo) {
        return new EntityRunCommand(command, delay, aInfo);
    }

}
