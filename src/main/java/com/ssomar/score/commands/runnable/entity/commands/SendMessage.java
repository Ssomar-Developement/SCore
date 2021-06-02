package com.ssomar.score.commands.runnable.entity.commands;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommandTemplate;

public class SendMessage extends EntityCommandTemplate{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		String message="";
		for(String s: args) {
			//SsomarDev.testMsg("cmdarg> "+s);
			message= message+s+" ";
		}
		message = message.substring(0, message.length()-1);
		sm.sendMessage(p, message);
	}

	@Override
	public String verify(List<String> args) {
		return "";
	}

}
