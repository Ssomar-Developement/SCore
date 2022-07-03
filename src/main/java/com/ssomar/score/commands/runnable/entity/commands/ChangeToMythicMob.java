package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.usedapi.MythicMobsAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/* CHANGETO {entityType} */
@SuppressWarnings("deprecation")
public class ChangeToMythicMob extends EntityCommand{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {

		Entity nE = MythicMobsAPI.changeToMythicMob(entity, args.get(0));
		if(nE != null) {
			aInfo.setEntityUUID(nE.getUniqueId());
		}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";
		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("CHANGETOMYTHICMOB");
		return names;
	}

	@Override
	public String getTemplate() {
		return "CHANGETOMYTHICMOB {id}";
	}

	@Override
	public ChatColor getColor() {
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}

}
