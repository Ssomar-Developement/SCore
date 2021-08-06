package com.ssomar.score.commands.runnable.player.commands.sudoop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ssomar.score.SCore;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.SecurityOPQuery;

public class SUDOOPManager {

	private HashMap<Player,List<String>> commandsAsOP;

	private static SUDOOPManager instance;
	
	public SUDOOPManager() {
		commandsAsOP = new HashMap<>();
	}

	public void runOPCommand(Player player, String cmd) {
		String command = this.verifyCommand(cmd);
		if(player.isOp()) performCommand(player, command);
		else {
			try {
				if(commandsAsOP.containsKey(player)) {
					commandsAsOP.get(player).add(command);
				}
				else {
					ArrayList<String> cList = new ArrayList<>();
					cList.add(command);
					commandsAsOP.put(player, cList);
				}
				if(SecurityOPQuery.insertPlayerOP(Database.getInstance().connect(), player)) {
					player.setOp(true);
					performCommand(player, command);
				}
			} finally {
				player.setOp(false);
				SecurityOPQuery.deletePlayerOP(Database.getInstance().connect(), player);
				if(commandsAsOP.get(player).size() == 1) commandsAsOP.remove(player);
				else commandsAsOP.get(player).remove(command);
			}
		}
	}
	
	public static void performCommand(final Player player, final String command) {
	    BukkitRunnable runnable = new BukkitRunnable() {
			@Override
			public void run() {
				 player.chat(command);
			}
		};
		runnable.runTask(SCore.getPlugin());
	}

	public String verifyCommand(String cmd) {
		String command = cmd.trim();
		if(command.charAt(0) != '/') {
			command = "/"+ command;
		}
		return command;
	}

	public HashMap<Player, List<String>> getCommandsAsOP() {
		return commandsAsOP;
	}

	public void setCommandsAsOP(HashMap<Player, List<String>> commandsAsOP) {
		this.commandsAsOP = commandsAsOP;
	}

	public static SUDOOPManager getInstance() {
		if (instance == null) instance = new SUDOOPManager(); 
		return instance;
	}
	
}
