package com.ssomar.score.commands.runnable;

import java.util.List;

import org.bukkit.ChatColor;

public interface Command {
	
	public List<String> getNames();
	
	public String getTemplate();
	
	public ChatColor getColor();
	
	public ChatColor getExtraColor();

}
