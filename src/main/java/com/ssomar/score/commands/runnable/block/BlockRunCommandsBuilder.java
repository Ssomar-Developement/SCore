package com.ssomar.score.commands.runnable.block;

import java.util.List;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.RunCommandsBuilder;

public class BlockRunCommandsBuilder extends RunCommandsBuilder{

	public BlockRunCommandsBuilder(List<String> commands, ActionInfo actionInfo) {
		super(commands, actionInfo);
	}

	@Override
	public RunCommand buildRunCommand(Integer delay, String command, ActionInfo aInfo) {
		return new BlockRunCommand(command, delay, aInfo);
	}

}
