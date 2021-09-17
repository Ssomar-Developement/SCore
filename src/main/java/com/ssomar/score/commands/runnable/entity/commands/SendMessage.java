package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;

public class SendMessage extends EntityCommand{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
		StringBuilder message= new StringBuilder();
		for(String s: args) {
			//SsomarDev.testMsg("cmdarg> "+s);
			message.append(s).append(" ");
		}
		message = new StringBuilder(message.substring(0, message.length() - 1));
		sm.sendMessage(p, message.toString());
	}

	@Override
	public String verify(List<String> args) {
		return "";
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("SENDMESSAGE");
		return names;
	}

	@Override
	public String getTemplate() {
		return "SENDMESSAGE {you msg here}";
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
