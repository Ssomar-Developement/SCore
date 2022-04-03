package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GlacialFreeze extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		int time = 20;
		
		try {
			time = Integer.parseInt(args.get(0));
			receiver.setFreezeTicks(time);
		}catch(Exception e){
			return;
		}
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String frontdash= "GLACIAL_FREEZE {time in ticks}";
		if(args.size()>1) error= tooManyArgs+frontdash;
		else if(args.size()<1) error = notEnoughArgs+frontdash;
		else if(args.size()==1) { 
			try {
				Integer.valueOf(args.get(0));
			}catch(NumberFormatException e){
				error = invalidTime+args.get(0)+" for command: "+frontdash;
			}
		}

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("GLACIAL_FREEZE");
		return names;
	}

	@Override
	public String getTemplate() {
		return "GLACIAL_FREEZE {time in ticks}";
	}

	@Override
	public ChatColor getColor() {
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}

}
