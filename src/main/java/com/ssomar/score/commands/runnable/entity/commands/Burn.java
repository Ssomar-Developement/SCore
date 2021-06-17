package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommandTemplate;

/* BURN {timeinsecs} */
public class Burn extends EntityCommandTemplate{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			if(args.size()==0) {
				entity.setFireTicks(20*10);
			}
			else {
				double time= Double.valueOf(args.get(0));
				entity.setFireTicks(20* (int)time);
			}
		}catch(Exception e) {}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";
		
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

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("BURN");
		return names;
	}

	@Override
	public String getTemplate() {
		return "BURN {timeinsecs}";
	}

}
