package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;

public class FrontDash extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		double amount = 5;

		try {
			amount = Double.valueOf(args.get(0));
		}catch(NumberFormatException e){
			return;
		}

		double customY = 0;
		if(args.size()>=2) {
			try {
				customY = Double.valueOf(args.get(1));
			}catch(NumberFormatException e){
				return;
			}
		}

		Location pLoc = receiver.getLocation();
		pLoc.setPitch(0);
		Vector v = pLoc.getDirection();
		v.multiply(amount);
		if(customY != 0) {
			Vector vec = new Vector();
			vec.setY(customY);
			v.add(vec);
		}
		receiver.setVelocity(v);

	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String frontdash = "FRONTDASH {number} [custom y]";
		if(args.size()>2) error= tooManyArgs+frontdash;
		else if(args.size() < 1) error = notEnoughArgs+frontdash;
		else { 
			try {
				if(!args.get(0).contains("%")) {
					Double.valueOf(args.get(0));
				}
			}catch(NumberFormatException e){
				error = invalidTime+args.get(0)+" for command: "+frontdash;
			}

			if(args.size() >= 2) {
				try {
					if(!args.get(1).contains("%")) {
						Double.valueOf(args.get(1));
					}
				}catch(NumberFormatException e){
					error = invalidTime+args.get(1)+" for command: "+frontdash;
				}
			}
		}

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("FRONTDASH");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "FRONTDASH {number} [custom y]";
	}

	@Override
	public ChatColor getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
