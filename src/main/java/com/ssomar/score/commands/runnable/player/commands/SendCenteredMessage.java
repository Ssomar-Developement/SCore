package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.messages.DefaultFontInfo;

/* SENDCENTEREDMESSAGE {message} */
public class SendCenteredMessage extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		String message = "";
		for(String s: args) {
			//SsomarDev.testMsg("cmdarg> "+s);
			message = message+s+" ";
		}
		message = message.substring(0, message.length()-1);
		DefaultFontInfo.sendCenteredMessage(receiver, message);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String message= "SENDCENTEREDMESSAGE {message}";
		if(args.size()<1) error = notEnoughArgs+message;

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("SENDCENTEREDMESSAGE");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "SENDCENTEREDMESSAGE {message}";
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
