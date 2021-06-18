package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

/* SETHEALTH {amount} */
@SuppressWarnings("deprecation")
public class SetHealth extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			if(args.size()==0) {
				receiver.setHealth(receiver.getMaxHealth());
			}
			else {
				double health= Double.valueOf(args.get(0));
				receiver.setHealth(health);
			}
		}catch(Exception e) {}
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String sethealth= "SETHEALTH {amount}";
		if(args.size()>1) error= tooManyArgs+sethealth;
		else if(args.size()==1 && !args.get(0).contains("%")) { 
			try {
				Double.valueOf(args.get(0));
			}catch(NumberFormatException e){
				error = invalidTime+args.get(0)+" for command: "+sethealth;
			}
		}

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("SETHEALTH");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "SETHEALTH {amount}";
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getExtraColor() {
		// TODO Auto-generated method stub
		return null;
	}
}
