package com.ssomar.score.commands.runnable.player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.RunCommandsBuilder;

import java.util.List;

public class PlayerRunCommandsBuilder extends RunCommandsBuilder {

    public PlayerRunCommandsBuilder(List<String> commands, ActionInfo actionInfo) {
        super(commands, actionInfo);
    }

    @Override
    public RunCommand buildRunCommand(Integer delay, String command, ActionInfo aInfo) {
        return new PlayerRunCommand(command, delay, aInfo);
    }

}
