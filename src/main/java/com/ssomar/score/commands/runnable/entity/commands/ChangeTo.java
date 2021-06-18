package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommandTemplate;

/* CHANGETO {entityType} */
public class ChangeTo extends EntityCommandTemplate{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		/* EXCEPTION */
	}

	@SuppressWarnings("deprecation")
	@Override
	public String verify(List<String> args) {
		String error = "";
		
		String changeto= "CHANGETO {entityType}";
		if(args.size()<1) error = notEnoughArgs+changeto;
		else if(args.size()==1) {
			if(EntityType.fromName(args.get(0))==null) error = invalidEntityType+args.get(0)+" for command: "+changeto;
		}
		else error= tooManyArgs+changeto;
		
		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("CHANGETO");
		return names;
	}

	@Override
	public String getTemplate() {
		return "CHANGETO {entityType}";
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
