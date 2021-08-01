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
import com.ssomar.score.commands.runnable.player.PlayerRunCommand;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.PlayerCommandsQuery;

public class CommandsHandler implements Listener {

	private static CommandsHandler instance;

	/* DelayedCommands by RunCommand UUID */
	private Map<UUID, RunCommand> delayedCommandsByRcUuid;

	/* DelayedCommands by receiver UUID */
	private Map<UUID, List<RunCommand>> delayedCommandsByReceiverUuid;

	/* for "morph item" timing between delete item and regive item (2 ticks)  player */
	private List<Player> stopPickup;

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
		System.out.println(" >>> "+p.getName()+ " "+commands.size());

		PlayerCommandsQuery.insertCommand(Database.getInstance().connect(), commands);

		this.removeAllDelayedCommands(p.getUniqueId());
	}

	public static void closeServerSaveAll() {
//		List<Cooldown> cooldowns = CooldownsManager.getInstance().getAllCooldowns();
//
//		CooldownsQuery.insertCooldowns(Database.getInstance().connect(), cooldowns);
//
//		CooldownsManager.getInstance().clearCooldowns();
	}


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
				System.out.println(">>>>>> Yes add :: "+delayedCommandsByReceiverUuid.size());
			}
		}
	}

	public void removeDelayedCommand(UUID uuid, @Nullable UUID receiverUUID) {
		if(delayedCommandsByRcUuid.containsKey(uuid) ) {
			BukkitTask task;
			if ((task = delayedCommandsByRcUuid.get(uuid).getTask()) != null) task.cancel();
			delayedCommandsByRcUuid.remove(uuid);
		}
		if(receiverUUID != null && delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {
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
		
		System.out.println(">>>>>> Yess remove :: "+delayedCommandsByReceiverUuid.size());
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
			System.out.println(">>>>>> Yes");
			List<RunCommand> runCommands = delayedCommandsByReceiverUuid.get(receiverUUID);
			//System.out.println(">>>>>> Yes2 :: "+runCommands.size());
			for(RunCommand rC : runCommands) {
				if(rC instanceof PlayerRunCommand) commands.add((PlayerRunCommand) rC);
			}
		}
		else {
			//System.out.println(">>>>>> Nooo :: "+delayedCommandsByReceiverUuid.size());
		}
		return commands;
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
