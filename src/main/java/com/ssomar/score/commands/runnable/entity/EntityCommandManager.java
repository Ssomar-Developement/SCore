package com.ssomar.score.commands.runnable.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;

import com.ssomar.score.commands.runnable.Command;
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
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;


public class EntityCommandManager {

	private static EntityCommandManager instance;
	
	private List<EntityCommandTemplate> commands;
	
	private EntityCommandManager() {
		commands = new ArrayList<>();
		commands.add(new TeleportPosition());
		commands.add(new TeleportEntityToPlayer());
		commands.add(new TeleportPlayerToEntity());
		commands.add(new ParticleCommand());
		commands.add(new SendMessage());
		commands.add(new Kill());
		commands.add(new ChangeTo());
		commands.add(new DropItem());
		commands.add(new DropExecutableItem());
		commands.add(new Heal());
		commands.add(new Damage());
		commands.add(new SetBaby());
		commands.add(new SetAdult());
		commands.add(new SetName());
		commands.add(new Burn());
		commands.add(new CustomDash1());
		commands.add(new SetGlow());
		commands.add(new RemoveGlow());
		commands.add(new StrikeLightning());
	}
	
	public EntityCommandTemplate getEntityCommand(String entry) {
		for(EntityCommandTemplate cmd : commands) {
			for(String name: cmd.getNames()) {
				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
					return cmd;
				}
			}
		}
		return null;
	}

	/*
	 *  return "" if no error else return the error
	 */
	public String verifArgs(EntityCommandTemplate eC, List<String> args) {
		return eC.verify(args);
	}


	public boolean isValidEntityCommand(String entry) {
		for(EntityCommandTemplate cmd : commands) {
			for(String name: cmd.getNames()) {
				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}


	public List<String> getECArgs(String entry) {
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

	
	public List<String> getEntityCommands(SPlugin sPlugin, List<String> commands, List<String> errorList, String id) {

		List<String> result = new ArrayList<>();


		for (int i = 0; i < commands.size(); i++) {

			String command = StringConverter.coloredString(commands.get(i));

			/*
			 * if (command.contains("\\{")) command= command.replaceAll("\\{", ""); if
			 * (command.contains("\\}")) command= command.replaceAll("\\}", "");
			 */

			if (EntityCommandManager.getInstance().isValidEntityCommand(commands.get(i)) && !commands.get(i).contains("//")) {
				EntityCommandTemplate eC = this.getEntityCommand(command);
				List<String> args = this.getECArgs(command);

				String error = "";
				if (!(error = this.verifArgs(eC, args)).isEmpty()) {
					errorList.add(sPlugin+" " + error + " for item: " + id);
					continue;
				}
			}
			result.add(command);
		}
		return result;
	}
	
	public static EntityCommandManager getInstance() {
		if(instance == null) instance = new EntityCommandManager();
		return instance;
	}

	public List<EntityCommandTemplate> getCommands() {
		return commands;
	}
	
	public Map<String, String> getCommandsDisplay() {
		Map<String, String> result = new HashMap<>();
		for(Command c : this.commands) {

			ChatColor extra = c.getExtraColor();
			if(extra == null) extra = ChatColor.DARK_PURPLE;

			ChatColor color = c.getColor();
			if(color == null) color = ChatColor.LIGHT_PURPLE;

			result.put(extra+"["+color+"&l"+c.getNames().get(0)+extra+"]", c.getTemplate());
		}
		return result;
	}

	public void setCommands(List<EntityCommandTemplate> commands) {
		this.commands = commands;
	}

}
