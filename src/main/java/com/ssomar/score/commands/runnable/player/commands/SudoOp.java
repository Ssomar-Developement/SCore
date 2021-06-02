package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.SUDOOPManager;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

/* SUDOOP {command} */
public class SudoOp extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		String command2 ="";
		for(String s: args) {
			command2= command2+s+" ";
		}
		command2 = command2.substring(0, command2.length()-1);
		SUDOOPManager.getInstance().runOPCommand(receiver, command2);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String sudoop= "SUDOOP {command}";
		if(args.size()<1) error = notEnoughArgs+sudoop;

		return error;
	}

}
