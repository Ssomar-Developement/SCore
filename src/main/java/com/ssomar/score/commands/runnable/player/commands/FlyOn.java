package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ssomar.executableitems.fly.FlyManager;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;

/* FLY ON */
public class FlyOn extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		receiver.setAllowFlight(true);
		FlyManager.getInstance().addPlayerWithFly(p);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";
		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("FLY ON");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "FLY ON";
	}

	@Override
	public ChatColor getColor() {
		// TODO Auto-generated method stub
		return ChatColor.BLUE;
	}

	@Override
	public ChatColor getExtraColor() {
		return ChatColor.AQUA;
	}
}
