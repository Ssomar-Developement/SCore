package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

/* SUDO {command} */
public class Sudo extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		String command2 ="";
		for(String s: args) {
			command2= command2+s+" ";
		}
		command2 = command2.substring(0, command2.length()-1);

		/*if(command2.startsWith("//")) {
			command2 = command2.substring(1, command2.length());
		}
		else */if(command2.startsWith("/")) {
			command2 = command2.substring(1, command2.length());
		}
		receiver.chat("/"+command2);
		//p.performCommand(finalCommand);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String sudo= "SUDO {command}";
		if(args.size()<1) error = notEnoughArgs+sudo;

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("SUDO");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "SUDO {command}";
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
