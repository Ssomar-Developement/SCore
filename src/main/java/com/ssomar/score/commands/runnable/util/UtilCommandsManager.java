package com.ssomar.score.commands.runnable.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;

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
	
	public Map<String, String> getCommandsDisplay() {
		Map<String, String> result = new HashMap<>();
		for(Command c : this.commands) {

			ChatColor extra = c.getExtraColor();
			if(extra == null) extra = ChatColor.GOLD;

			ChatColor color = c.getColor();
			if(color == null) color = ChatColor.YELLOW;

			result.put(extra+"["+color+"&l"+c.getNames().get(0)+extra+"]", c.getTemplate());
		}
		return result;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

}
