package com.ssomar.score.commands.runnable;

import java.util.List;

import org.bukkit.Color;

public interface Command {
	
	public List<String> getNames();
	
	public String getTemplate();
	
	public Color getColor();
	
	public Color getExtraColor();

}
