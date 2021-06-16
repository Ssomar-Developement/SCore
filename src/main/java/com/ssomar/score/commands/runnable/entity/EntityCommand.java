package com.ssomar.score.commands.runnable.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssomar.score.commands.runnable.entity.commands.Burn;
import com.ssomar.score.commands.runnable.entity.commands.ChangeTo;
import com.ssomar.score.commands.runnable.entity.commands.CustomDash1;
import com.ssomar.score.commands.runnable.entity.commands.Damage;
import com.ssomar.score.commands.runnable.entity.commands.DropExecutableItem;
import com.ssomar.score.commands.runnable.entity.commands.DropItem;
import com.ssomar.score.commands.runnable.entity.commands.Heal;
import com.ssomar.score.commands.runnable.entity.commands.Kill;
import com.ssomar.score.commands.runnable.entity.commands.ParticleCommand;
import com.ssomar.score.commands.runnable.entity.commands.RemoveGlow;
import com.ssomar.score.commands.runnable.entity.commands.SendMessage;
import com.ssomar.score.commands.runnable.entity.commands.SetAdult;
import com.ssomar.score.commands.runnable.entity.commands.SetBaby;
import com.ssomar.score.commands.runnable.entity.commands.SetGlow;
import com.ssomar.score.commands.runnable.entity.commands.SetName;
import com.ssomar.score.commands.runnable.entity.commands.StrikeLightning;
import com.ssomar.score.commands.runnable.entity.commands.TeleportEntityToPlayer;
import com.ssomar.score.commands.runnable.entity.commands.TeleportPlayerToEntity;
import com.ssomar.score.commands.runnable.entity.commands.TeleportPosition;
import com.ssomar.score.utils.StringConverter;


public enum EntityCommand {

	TELEPORT_POSITION ("TELEPORT POSITION"),
	TELEPORT_ENTITY_TO_PLAYER ("TELEPORT ENTITY TO PLAYER"),
	TELEPORT_PLAYER_TO_ENTITY ("TELEPORT PLAYER TO ENTITY"),
	PARTICLE ("PARTICLE"),
	SENDMESSAGE ("SENDMESSAGE"),
	KILL ("KILL"),
	CHANGETO ("CHANGETO"),
	DROPITEM ("DROPITEM"),
	DROPEXECUTABLEITEM ("DROPEXECUTABLEITEM"),
	HEAL ("HEAL"),
	DAMAGE ("DAMAGE"),
	SETBABY ("SETBABY"),
	SETADULT ("SETADULT"),
	SETNAME ("SETNAME"),
	BURN ("BURN"),
	CUSTOMDASH1 ("CUSTOMDASH1"),
	SETGLOW ("SETGLOW"),
	REMOVEGLOW ("REMOVEGLOW"),
	STRIKELIGHTNING ("STRIKELIGHTNING");

	private String[] names;

	EntityCommand(String... names) {
		this.names = names;
	}
	
	public static Map<EntityCommand, EntityCommandTemplate> getReferences(){
		Map<EntityCommand, EntityCommandTemplate> references = new HashMap<>();
		
		references.put(TELEPORT_POSITION, new TeleportPosition());
		references.put(TELEPORT_ENTITY_TO_PLAYER, new TeleportEntityToPlayer());
		references.put(TELEPORT_PLAYER_TO_ENTITY, new TeleportPlayerToEntity());
		references.put(PARTICLE, new ParticleCommand());
		references.put(SENDMESSAGE, new SendMessage());
		references.put(KILL, new Kill());
		references.put(CHANGETO, new ChangeTo());
		references.put(DROPITEM, new DropItem());
		references.put(DROPEXECUTABLEITEM, new DropExecutableItem());
		references.put(HEAL, new Heal());
		references.put(DAMAGE, new Damage());
		references.put(SETBABY, new SetBaby());
		references.put(SETADULT, new SetAdult());
		references.put(SETNAME, new SetName());
		references.put(BURN, new Burn());
		references.put(CUSTOMDASH1, new CustomDash1());
		references.put(SETGLOW, new SetGlow());
		references.put(REMOVEGLOW, new RemoveGlow());
		references.put(STRIKELIGHTNING, new StrikeLightning());
		
		return references;
	}

	public static String verifArgs(EntityCommand eC, List<String> args) {
		return EntityCommand.getReferences().get(eC).verify(args);
	}
	
	public static boolean isValidEntityCommads(String entry) {
		for(EntityCommand entityCommands : values()) {
			for(String name: entityCommands.getNames()) {
				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}


	public static EntityCommand getEntityCommand(String entry) {
		for(EntityCommand entityCommands : values()) {
			for(String name: entityCommands.getNames()) {
				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
					return entityCommands;
				}
			}
		}
		return null;
	}

	public static List<String> getECArgs(String entry) {
		List<String> args = new ArrayList<>();
		boolean first= true;
		boolean second= false;
		boolean third= false;
		if(entry.toUpperCase().startsWith("TELEPORT POSITION")) second=true;
		else if(entry.toUpperCase().startsWith("TELEPORT ENTITY TO PLAYER")
				|| entry.toUpperCase().startsWith("TELEPORT PLAYER TO ENTITY")) {
			second=true;
			third=true;
		}
		for(String s : entry.split(" ")) {
			if(first) {
				first=false;
				continue;
			}
			if(second) {
				second=false;
				continue;
			}
			if(third) {
				third=false;
				continue;
			}
			args.add(s);
		}
		return args;
	}

	public boolean containsThisName(String entry) {
		for(String name : getNames()) {
			if(name.equalsIgnoreCase(entry)) return true;
		}
		return false;
	}
	
	public static List<String> getEntityCommands(List<String> commands, List<String> errorList, String id) {

		List<String> result = new ArrayList<>();


		for (int i = 0; i < commands.size(); i++) {

			String command = StringConverter.coloredString(commands.get(i));

			/*
			 * if (command.contains("\\{")) command= command.replaceAll("\\{", ""); if
			 * (command.contains("\\}")) command= command.replaceAll("\\}", "");
			 */

			if (EntityCommand.isValidEntityCommads(commands.get(i)) && !commands.get(i).contains("//")) {
				EntityCommand bc = EntityCommand.getEntityCommand(command);
				List<String> args = EntityCommand.getECArgs(command);

				String error = "";
				if (!(error = EntityCommand.verifArgs(bc, args)).isEmpty()) {
					errorList.add("[ExecutableItems] " + error + " for item: " + id);
					continue;
				}
			}
			result.add(command);
		}
		return result;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}
}
