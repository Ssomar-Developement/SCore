package com.ssomar.score.commands.runnable.entity;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.CommandsManager;
import com.ssomar.score.commands.runnable.RunConsoleCommand;

public class EntityCommandsExecutor extends CommandsExecutor{

	/* Entity who are affected by the commands */
	private Entity entity;



	public EntityCommandsExecutor(List<String> commands, Player player, Entity entity, ActionInfo actionInfo) {
		super(commands, player, actionInfo);
		this.entity=entity;
	}

	public EntityCommandsExecutor(List<String> commands, Player player, boolean silenceOutput, Entity entity, ActionInfo actionInfo) {
		super(commands, player, silenceOutput, actionInfo);
		this.entity=entity;
	}

	public String replaceLocation(String command) {	
		Location loc = entity.getLocation();		
		return replaceLocation(command, loc.getX(), loc.getY(), loc.getZ(), loc.getWorld());	
	}


	public void runEntityCommands(boolean silenceOutput) {

		for(Integer d : this.getFinalCommands().keySet()) {
			for(String commandz : this.getFinalCommands().get(d)) {

				UUID uuid = UUID.randomUUID();

				int id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask( SCore.getPlugin() , new Runnable(){


					public void run(){			

						String command = replacePlaceholder(replaceLocation(commandz));	

						if(!entity.isDead()) {

							if(command.contains("ei-giveslot")) {
								try {
									String playeName= command.split("ei-giveslot ")[1].split(" ")[0];
									Player pgive = Bukkit.getPlayer(playeName);
									CommandsManager.getInstance().addStopPickup(pgive, 20);
								}catch(Exception e) {}
							}

							EntityCommandTemplate eC = EntityCommandManager.getInstance().getEntityCommand(command);

							List<String> args = EntityCommandManager.getInstance().getECArgs(command);

							if(eC!=null) {
	
								if(eC.getNames().contains("CHANGETO")) {
									try {
										if(!entity.isDead()) {
											Entity newEnt = entity.getWorld().spawnEntity(entity.getLocation(), EntityType.valueOf(command.split(" ")[1]));
											newEnt.setCustomName(entity.getCustomName());
											newEnt.setCustomNameVisible(entity.isCustomNameVisible());
											newEnt.setGlowing(entity.isGlowing());
											//.... if needed for the rest

											entity.remove();
											setEntity(newEnt);
										}
									}catch(Exception e) {}
								}
								else {
									eC.run(getPlayer(), entity, args, getActionInfo(), silenceOutput);
								}
								
							}
							else {
								if(command.charAt(0)=='/') {
									command=  command.substring(1, command.length());
								}
								RunConsoleCommand.runConsoleCommand(command, silenceOutput);
							}
							if(getPlayer() != null) CommandsManager.getInstance().removeDelayedCommand(getPlayer(), uuid);
						}
					}
				}, d);
				if(getPlayer() != null) CommandsManager.getInstance().addDelayedCommand(getPlayer(), uuid, id);
			}
		}

	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}


}
