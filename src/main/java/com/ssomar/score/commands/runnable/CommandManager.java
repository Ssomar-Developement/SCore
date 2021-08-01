package com.ssomar.score.commands.runnable;

import java.util.List;

public interface CommandManager {

	public abstract SCommand getCommand(String brutCommand);
	
	public List<String> getArgs(String command);
	
}
