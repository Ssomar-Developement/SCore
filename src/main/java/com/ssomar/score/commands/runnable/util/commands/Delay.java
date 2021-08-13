package com.ssomar.score.commands.runnable.util.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.ssomar.score.commands.runnable.SCommand;

public class Delay implements SCommand{

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("DELAY");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "DELAY {number}";
	}

	@Override
	public ChatColor getColor() {
		// TODO Auto-generated method stub
		return ChatColor.YELLOW;
	}

	@Override
	public ChatColor getExtraColor() {
		return ChatColor.GOLD;
	}

}
