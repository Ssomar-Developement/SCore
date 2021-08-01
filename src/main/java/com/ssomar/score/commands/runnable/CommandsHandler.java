package com.ssomar.score.commands.runnable;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.player.PlayerRunCommand;

public class CommandsHandler {	
	
	private static CommandsHandler instance;
	
	/* DelayedCommands by RunCommand UUID */
	private Map<UUID, RunCommand> delayedCommandsByRcUuid;
	
	/* DelayedCommands by receiver UUID */
	private Map<UUID, List<RunCommand>> delayedCommandsByReceiverUuid;

	/* for "morph item" timing between delete item and regive item (2 ticks)  player */
	private List<Player> stopPickup;
	
	public CommandsHandler() {
		delayedCommandsByRcUuid = new HashMap<>();
		delayedCommandsByReceiverUuid = new HashMap<>();
		stopPickup = new ArrayList<>();
	}
	
	public void addDelayedCommand(@NotNull RunCommand command) {
		delayedCommandsByRcUuid.put(command.getUuid(), command);
		if(command instanceof PlayerRunCommand) {
			UUID receiverUUID = ((PlayerRunCommand)command).getReceiverUUID();
			if(delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {
				delayedCommandsByReceiverUuid.get(receiverUUID).add(command);
			}
			else {
				List<RunCommand> list = new ArrayList<>();
				list.add(command);
				delayedCommandsByReceiverUuid.put(((PlayerRunCommand)command).getReceiverUUID(), list);
			}
		}
	}
	
	public void removeDelayedCommand(UUID uuid, @Nullable UUID receiverUUID) {
		if(delayedCommandsByRcUuid.containsKey(uuid) ) {
			BukkitTask task;
			if ((task = delayedCommandsByRcUuid.get(uuid).getTask()) != null) task.cancel();
			delayedCommandsByRcUuid.remove(uuid);
		}
		if(receiverUUID != null && delayedCommandsByReceiverUuid.containsKey(receiverUUID) ) {
			List<RunCommand> runCommands = delayedCommandsByReceiverUuid.get(receiverUUID);
			RunCommand toDelete = null;
			
			for(RunCommand rC : runCommands) {
				if(rC.getUuid().equals(uuid)) {
					toDelete = rC;
					BukkitTask task;
					if ((task = rC.getTask()) != null) task.cancel();
				}
			}
			if(toDelete != null) runCommands.remove(toDelete);

			if(runCommands.isEmpty()) delayedCommandsByReceiverUuid.remove(receiverUUID);
		}
	}
	
	public void removeAllDelayedCommands(UUID receiverUUID) {
		if(delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {
			List<RunCommand> runCommands = delayedCommandsByReceiverUuid.get(receiverUUID);
			
			for(RunCommand rC : runCommands) {
				this.removeDelayedCommand(rC.getUuid(), null);
			}
			
			delayedCommandsByReceiverUuid.remove(receiverUUID);
		}
	}

	public void addStopPickup(Player p, Integer delay) {
		stopPickup.add(p);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SCore.getPlugin() , new Runnable(){
			public void run(){			
				stopPickup.remove(p);
			}
		}, delay);
	}


	public List<Player> getStopPickup() {
		return stopPickup;
	}


	public void setStopPickup(List<Player> stopPickup) {
		this.stopPickup = stopPickup;
	}
	
	public static CommandsHandler getInstance() {
		if (instance == null) instance = new CommandsHandler(); 
		return instance;
	}
	
}
