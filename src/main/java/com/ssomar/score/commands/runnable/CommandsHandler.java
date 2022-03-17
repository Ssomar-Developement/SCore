package com.ssomar.score.commands.runnable;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.block.BlockRunCommand;
import com.ssomar.score.commands.runnable.entity.EntityRunCommand;
import com.ssomar.score.commands.runnable.player.PlayerRunCommand;
import com.ssomar.score.data.BlockCommandsQuery;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.EntityCommandsQuery;
import com.ssomar.score.data.PlayerCommandsQuery;

public class CommandsHandler implements Listener {

	private static CommandsHandler instance;

	/* DelayedCommands by RunCommand UUID */
	private final Map<UUID, RunCommand> delayedCommandsByRcUuid;

	/* DelayedCommands by receiver UUID */
	private final Map<UUID, List<RunCommand>> delayedCommandsByReceiverUuid;

	/* DelayedCommands by entity UUID */
	List<EntityRunCommand> delayedCommandsByEntityUuid;
	
	/* DelayedCommands by block UUID */
	List<BlockRunCommand> delayedCommandsByBlockUuid;

	/* for "morph item" timing between delete item and regive item (2 ticks)  player */
	private Map<Player, Long> stopPickup;

	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerJoinEvent(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		List<PlayerRunCommand> commands = PlayerCommandsQuery.selectCommandsForPlayer(Database.getInstance().connect(), p.getUniqueId());

		for(PlayerRunCommand pC : commands) {
			// run la command et va sauto dans les listes en haut
			pC.run();
		}

		PlayerCommandsQuery.deleteCommandsForPlayer(Database.getInstance().connect(), p.getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerQuitEvent(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		List<PlayerRunCommand> commands = getInstance().getDelayedCommandsWithReceiver(p.getUniqueId());
		List<PlayerRunCommand> commandsToSave = new ArrayList<>();
		for(PlayerRunCommand command : commands){
			if(!command.isRunOffline()){
				commandsToSave.add(command);
			}
		}

		PlayerCommandsQuery.insertCommand(Database.getInstance().connect(), commandsToSave, true);

		for(PlayerRunCommand command : commandsToSave){
			getInstance().removeDelayedCommand(command.getUuid(), p.getUniqueId());
		}
	}
	
	public void onEnable() {
		/* Quite useless because at the start of the server the entities seems not loaded and the Bukkit.getentity return null */
		List<EntityRunCommand> commands = EntityCommandsQuery.selectEntityCommands(Database.getInstance().connect());
		for(EntityRunCommand eCommand : commands) {
			eCommand.run();
		}
		EntityCommandsQuery.deleteEntityCommands(Database.getInstance().connect());
		
		
		List<BlockRunCommand> commands2 = BlockCommandsQuery.selectAllCommands(Database.getInstance().connect());
		for(BlockRunCommand bCommand : commands2) {
			bCommand.run();
		}
		BlockCommandsQuery.deleteCommands(Database.getInstance().connect());
	}

	public void onDisable() {
		/* Save all delayed commands in BDD */
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			List<PlayerRunCommand> commands = getInstance().getDelayedCommandsWithReceiver(p.getUniqueId());
			//System.out.println(" >>> "+p.getName()+ " "+commands.size());

			PlayerCommandsQuery.insertCommand(Database.getInstance().connect(), commands, false);

			getInstance().removeAllDelayedCommands(p.getUniqueId());
		}
		
		EntityCommandsQuery.insertCommand(Database.getInstance().connect(), this.delayedCommandsByEntityUuid);
		this.delayedCommandsByEntityUuid.clear();
		
		BlockCommandsQuery.insertCommand(Database.getInstance().connect(), this.delayedCommandsByBlockUuid);
		this.delayedCommandsByBlockUuid.clear();
	}


	public CommandsHandler() {
		delayedCommandsByRcUuid = new HashMap<>();
		delayedCommandsByReceiverUuid = new HashMap<>();
		delayedCommandsByEntityUuid = new ArrayList<>();
		delayedCommandsByBlockUuid = new ArrayList<>();
		stopPickup = new HashMap<>();
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
				//System.out.println(">>>>>> Yes add :: "+delayedCommandsByReceiverUuid.size());
			}
		}
		else if (command instanceof EntityRunCommand) {
			this.delayedCommandsByEntityUuid.add((EntityRunCommand)command);
		}
		else if (command instanceof BlockRunCommand) {
			this.delayedCommandsByBlockUuid.add((BlockRunCommand)command);
		}
	}

	public void removeDelayedCommand(UUID uuid, @Nullable UUID receiverUUID) {
		if(delayedCommandsByRcUuid.containsKey(uuid) ) {
			BukkitTask task;
			if ((task = delayedCommandsByRcUuid.get(uuid).getTask()) != null) task.cancel();
			delayedCommandsByRcUuid.remove(uuid);
		}
		
		/* ==================================== */
		RunCommand toDelete = null;

		for(RunCommand rC : delayedCommandsByEntityUuid) {
			if(rC.getUuid().equals(uuid)) {
				toDelete = rC;
				BukkitTask task;
				if ((task = rC.getTask()) != null) task.cancel();
			}
		}
		if(toDelete != null) delayedCommandsByEntityUuid.remove(toDelete);
		
		/* ==================================== */
		toDelete = null;
		
		for(RunCommand rC : delayedCommandsByBlockUuid) {
			if(rC.getUuid().equals(uuid)) {
				toDelete = rC;
				BukkitTask task;
				if ((task = rC.getTask()) != null) task.cancel();
			}
		}
		if(toDelete != null) delayedCommandsByBlockUuid.remove(toDelete);
		
		
		if(receiverUUID != null) {
			if(delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {

				List<RunCommand> runCommands = delayedCommandsByReceiverUuid.get(receiverUUID);
				toDelete = null;

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

		//System.out.println(">>>>>> Yess remove :: "+delayedCommandsByReceiverUuid.size());
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

	public List<PlayerRunCommand> getDelayedCommandsWithReceiver(UUID receiverUUID) {
		List<PlayerRunCommand> commands = new ArrayList<>();
		if(delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {
			List<RunCommand> runCommands = delayedCommandsByReceiverUuid.get(receiverUUID);
			for(RunCommand rC : runCommands) {
				if(rC instanceof PlayerRunCommand) commands.add((PlayerRunCommand) rC);
			}
		}
		return commands;
	}

	public void addStopPickup(Player p, Integer delay) {
		long time = System.currentTimeMillis()+(delay*50);
		//System.out.println("ADD "+p.getDisplayName()+ " time: "+time);
		stopPickup.put(p, time);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SCore.plugin , () -> {
			if(stopPickup.containsKey(p) && stopPickup.get(p) == time){
				stopPickup.remove(p);
			}
		} , delay);
	}

	public boolean hasStopPickup(@NotNull  Player p){
		long time = System.currentTimeMillis();
		//System.out.println("pickup "+CommandsHandler.getInstance().getStopPickup().get(p)+" actual "+time);
		boolean stop = stopPickup.containsKey(p) && CommandsHandler.getInstance().getStopPickup().get(p) > time;
		if(!stop){
			stopPickup.remove(p);
		}
		return stop;
	}

	//FAIRE AVEC LHEURE DE FIN CEST MIEUX

	public Map<Player, Long> getStopPickup() {
		return stopPickup;
	}


	public void setStopPickup(Map<Player, Long> stopPickup) {
		this.stopPickup = stopPickup;
	}

	public static CommandsHandler getInstance() {
		if (instance == null) instance = new CommandsHandler(); 
		return instance;
	}

}
