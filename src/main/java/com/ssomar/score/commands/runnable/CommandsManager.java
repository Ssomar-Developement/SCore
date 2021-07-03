package com.ssomar.score.commands.runnable;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.player.PlayerCommandsExecutor;



public class CommandsManager {	
	
	private static CommandsManager instance;
	
	private HashMap<String, List<String>> disconnectedPlayerCommands;

	private HashMap<String, List<String>> serverOffPlayerCommands;

	private HashMap<String,Map<UUID, Integer>> delayedCommands;

	/* for "morph item" timing between delete item and regive item (2 ticks)  player */
	private List<Player> stopPickup;
	
	public CommandsManager() {
		disconnectedPlayerCommands = new HashMap<>();
		serverOffPlayerCommands = new HashMap<>();
		delayedCommands = new HashMap<>();
		stopPickup = new ArrayList<>();
	}


	public boolean runServerOffCommands(Player p) {
		if(serverOffPlayerCommands.containsKey(p.getName())) {
			List<String> commands = serverOffPlayerCommands.get(p.getName());
			new PlayerCommandsExecutor(commands, p, true, p, null).runPlayerCommands(true);
			serverOffPlayerCommands.remove(p.getName());
		}
		return true;
	}

	public boolean runDisconnectedCommands(Player p) {
		if(disconnectedPlayerCommands.containsKey(p.getName())) {
			List<String> commands = disconnectedPlayerCommands.get(p.getName());
			new PlayerCommandsExecutor(commands, p, true, p, null).runPlayerCommands(true);
			disconnectedPlayerCommands.remove(p.getName());
		}
		return true;
	}
	
	public void addDisconnectedPlayerCommand(Player p, String command) {
		if(disconnectedPlayerCommands.containsKey(p.getName())) {
			disconnectedPlayerCommands.get(p.getName()).add(command);
		}else {
			List<String> list = new ArrayList<>();
			list .add(command);
			disconnectedPlayerCommands.put(p.getName(), list);
		}
	}
	
	public void removeDisconnectedPlayerCommand(Player p, String command) {
		if(disconnectedPlayerCommands.containsKey(p.getName())) {
			disconnectedPlayerCommands.get(p.getName()).remove(command);
		}
	}

	
	public HashMap<String, List<String>> getDisconnectedPlayerCommands() {
		return disconnectedPlayerCommands;
	}

	public void setDisconnectedPlayerCommands(HashMap<String, List<String>> disconnectedPlayerCommands) {
		this.disconnectedPlayerCommands = disconnectedPlayerCommands;
	}

	
	public void addDelayedCommand(Player p, UUID uuid, Integer id) {
		if(delayedCommands.containsKey(p.getName())) {
			delayedCommands.get(p.getName()).put(uuid, id);
		}
		else {
			Map<UUID, Integer> map = new HashMap<>();
			map.put(uuid, id);
			delayedCommands.put(p.getName(), map);
		}
	}
	
	public void removeDelayedCommand(Player p, UUID uuid) {
		if(delayedCommands.containsKey(p.getName())) {
			if(delayedCommands.get(p.getName()).containsKey(uuid)) delayedCommands.get(p.getName()).remove(uuid);
		}
	}

	public HashMap<String, Map<UUID, Integer>> getDelayedCommands() {
		return delayedCommands;
	}

	public void setDelayedCommands(HashMap<String, Map<UUID, Integer>> delayedCommands) {
		this.delayedCommands = delayedCommands;
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

	public void addServerOffPlayerCommand(Player p, String command) {
		if(serverOffPlayerCommands.containsKey(p.getName())) {
			serverOffPlayerCommands.get(p.getName()).add(command);
		}else {
			List<String> list = new ArrayList<>();
			list.add(command);
			serverOffPlayerCommands.put(p.getName(), list);
		}
	}
	
	public void removeserverOffPlayerCommand(Player p, String command) {
		if(serverOffPlayerCommands.containsKey(p.getName())) {
			serverOffPlayerCommands.get(p.getName()).remove(command);
		}
	}

	public HashMap<String, List<String>> getServerOffPlayerCommands() {
		return serverOffPlayerCommands;
	}

	public void setServerOffPlayerCommands(HashMap<String, List<String>> serverOffPlayerCommands) {
		this.serverOffPlayerCommands = serverOffPlayerCommands;
	}
	
	public static CommandsManager getInstance() {
		if (instance == null) instance = new CommandsManager(); 
		return instance;
	}
	
}
