package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

/* SENDMESSAGE {message} */
public class SendMessage extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		String message="";
		for(String s: args) {
			//SsomarDev.testMsg("cmdarg> "+s);
			message= message+s+" ";
		}
		message = message.substring(0, message.length()-1);
		sm.sendMessage(receiver, message);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String message= "SENDMESSAGE {message}";
		if(args.size()<1) error = notEnoughArgs+message;

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("SENDMESSAGE");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "SENDMESSAGE {message}";
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
