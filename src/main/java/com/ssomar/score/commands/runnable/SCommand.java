package com.ssomar.score.commands.runnable;

import java.util.List;

import org.bukkit.ChatColor;

public interface SCommand {
	
	List<String> getNames();
	
	String getTemplate();
	
	ChatColor getColor();
	
	ChatColor getExtraColor();

}
