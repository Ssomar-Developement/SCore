package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

public class RegainFood extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		int regain=1;
		if(args.size()==1) {
			regain = Integer.valueOf(args.get(0));
		}
		receiver.setFoodLevel(receiver.getFoodLevel()+regain);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String regainHealth= "REGAIN FOOD {amount}";
		if(args.size()>1) error= tooManyArgs+regainHealth;
		else if(args.size()==1) { 
			try {
				Integer.valueOf(args.get(0));
			}catch(NumberFormatException e){
				error = invalidTime+args.get(0)+" for command: "+regainHealth;
			}
		}

		return error;
	}

}
