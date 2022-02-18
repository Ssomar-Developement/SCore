package com.ssomar.score.commands.runnable;

import com.ssomar.score.usedapi.MultiverseAPI;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.FilterManager;

import java.util.List;


public class RunConsoleCommand {

	public static void runConsoleCommand(String command, boolean silenceOutput) {
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

		FilterManager fM = FilterManager.getInstance();

		if(silenceOutput) fM.incCurrentlyInRun();

		BukkitRunnable runnable = new BukkitRunnable() {
			@Override
			public void run() {

				try{
					addWorldCompatibilityForExecute(command);
					Bukkit.dispatchCommand(console, command);
				}
				catch (Exception e) {
					e.printStackTrace();
					SCore.getPlugin().getLogger().severe(SCore.NAME_2+" ERROR WHEN THE CONSOLE COMMAND IS RUN !");
				}

			}
		};
		runnable.runTaskLater(SCore.getPlugin(), 1);


		BukkitRunnable runnable3 = new BukkitRunnable() {

			@Override
			public void run() {
				if(silenceOutput) fM.decrCurrentlyInRun();

			}
		};
		runnable3.runTaskLater(SCore.getPlugin(), 2);
	}

	public static String addWorldCompatibilityForExecute(String command){
		if(command.contains("in <<")){
			String [] spliter = command.split("in <<");
			if(spliter.length > 1){
				String [] spliter2 = command.split(">>");
				String worldName = spliter2[0];
				World world = MultiverseAPI.getWorld(worldName);
				if(world != null){
					List<Entity> entities = world.getEntities();
					if(entities.size() > 0){
						command = command.replace("in <<"+worldName+">>", "at @e[uuid"+entities.get(0).getUniqueId()+"]");
					}
				}
			}
		}
		return command;
	}
}
