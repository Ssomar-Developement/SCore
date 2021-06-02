package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

/* BURN {timeinsecs} */
public class Burn extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			if(args.size()==0) {
				receiver.setFireTicks(20*10);
			}
			else {
				double time= Double.valueOf(args.get(0));
				receiver.setFireTicks(20* (int)time);
			}
		}catch(Exception e) {}
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String burn= "BURN {timeinsecs}";
		if(args.size()>1) error= tooManyArgs+burn;
		else if(args.size()==1) { 
			try {
				Double.valueOf(args.get(0));
			}catch(NumberFormatException e){
				error = invalidTime+args.get(0)+" for command: "+burn;
			}
		}

		return error;
	}
}
