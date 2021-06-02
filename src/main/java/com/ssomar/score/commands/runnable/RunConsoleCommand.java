package com.ssomar.score.commands.runnable;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.FilterManager;


public class RunConsoleCommand {

	public static void runConsoleCommand(String command, boolean silenceOutput) {
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

		FilterManager fM = FilterManager.getInstance();

		if(silenceOutput) fM.incCurrentlyInRun();

		BukkitRunnable runnable = new BukkitRunnable() {
			@Override
			public void run() {

				try{
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
}
