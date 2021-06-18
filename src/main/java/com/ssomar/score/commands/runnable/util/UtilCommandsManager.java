package com.ssomar.score.commands.runnable.util;

import java.util.ArrayList;
import java.util.List;

import com.ssomar.score.commands.runnable.Command;

public class UtilCommandsManager {
	
	private static UtilCommandsManager instance;
	
	private List<Command> commands;
	
	public UtilCommandsManager() {
		List<Command> commands = new ArrayList<>();
		
		this.commands = commands;
	}
	
	public static UtilCommandsManager getInstance() {
		if(instance == null) instance = new UtilCommandsManager();
		return instance;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

}
