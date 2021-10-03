package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;

public class RegainHealth extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		double regain=1;
		if(args.size()==1) {
			regain = Double.parseDouble(args.get(0));
		}
		if(receiver.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() >= receiver.getHealth()+regain)
		receiver.setHealth(receiver.getHealth()+regain);
		else receiver.setHealth(receiver.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String regainHealth= "REGAIN HEALTH {amount}";
		if(args.size()>1) error= tooManyArgs+regainHealth;
		else if(args.size()==1) { 
			try {
				Double.valueOf(args.get(0));
			}catch(NumberFormatException e){
				error = invalidTime+args.get(0)+" for command: "+regainHealth;
			}
		}

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("REGAIN HEALTH");
		return names;
	}

	@Override
	public String getTemplate() {
		return "REGAIN HEALTH {amount}";
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
