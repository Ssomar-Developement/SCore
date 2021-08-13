package com.ssomar.score.commands.runnable.util.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.ssomar.score.commands.runnable.SCommand;

public class LoopEnd implements SCommand{

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("LOOP END");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "LOOP END";
	}

	@Override
	public ChatColor getColor() {
		// TODO Auto-generated method stub
		return ChatColor.YELLOW;
	}

	@Override
	public ChatColor getExtraColor() {
		// TODO Auto-generated method stub
		return ChatColor.GOLD;
	}
	
}
