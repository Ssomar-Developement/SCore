package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.utils.StringConverter;

/* SETNAME {name} */
public class SetName extends EntityCommand{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
		if(!entity.isDead()) {
			StringBuilder name = new StringBuilder();
			for(String s: args) {
				name.append(s).append(" ");
			}
			name = new StringBuilder(name.substring(0, name.length() - 1));
			try {
				entity.setCustomName(StringConverter.coloredString(name.toString()));
			}catch(Exception e) {}
		}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";
		
		String setname= "SETNAME {name}";
		if(args.size()<1) error = notEnoughArgs+setname;
		
		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("SETNAME");
		return names;
	}

	@Override
	public String getTemplate() {
		return "SETNAME {name}";
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
