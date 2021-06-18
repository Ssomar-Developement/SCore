package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

public class ActionbarCommandOff extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {}

	@Override
	public String verify(List<String> args) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("ACTIONBAR OFF");
		return names;
	}

	@Override
	public String getTemplate() {
		return "ACTIONBAR OFF";
	}

	@Override
	public ChatColor getColor() {
		return ChatColor.GREEN;
	}

	@Override
	public ChatColor getExtraColor() {
		return ChatColor.DARK_GREEN;
	}

}
