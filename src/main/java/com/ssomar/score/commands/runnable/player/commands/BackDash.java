package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;

public class BackDash extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		int amount;
		
		try {
			amount = Integer.parseInt(args.get(0));
		}catch(NumberFormatException e){
			return;
		}
		
		Location pLoc = receiver.getLocation();
		pLoc.setPitch(0);
		Vector v = pLoc.getDirection();
		v.multiply(-amount);
		receiver.setVelocity(v);
		
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String frontdash= "BACKDASH {number}";
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
		names.add("BACKDASH");
		return names;
	}

	@Override
	public String getTemplate() {
		return "BACKDASH {number}";
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
