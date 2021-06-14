package com.ssomar.score;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsManager;
import com.ssomar.score.commands.runnable.player.PlayerCommandsExecutor;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageInterface;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.data.CommandsQuery;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.SecurityOPQuery;
import com.ssomar.score.events.EventsHandler;
import com.ssomar.score.utils.Utils;

public final class SCore extends JavaPlugin {

	public static SCore plugin;
	
	public static final String NAME = "SCore";
	
	public static final String NAME_2 = "[SCore]";
	
	public static boolean hasPlaceholderAPI = false;
	
	public static boolean hasExecutableItems = false;
	
	public static boolean hasExecutableBlocks = false;
	
	public static boolean hasCustomPiglinsTrades = false;
	
	public static boolean hasSParkour = false;
	
	public static boolean hasWorldGuard = false;
	
	public static boolean hasVault = false;
	
	public static boolean hasIridiumSkyblock = false;

	
	@Override
	public void onEnable() {
		plugin = this;

		Utils.sendConsoleMsg("================ "+NAME_2+" ================");
		
		this.loadDependency();
		
		GeneralConfig.getInstance();
		
		MessageMain.getInstance().load();

		MessageMain.getInstance().loadMessagesOf(plugin, MessageInterface.getMessagesEnum(Message.values()));
		

		/* Database */
		Database.getInstance().load();
		
		/* Events instance part */	
		EventsHandler.getInstance().setup(this);
		


		Utils.sendConsoleMsg("================ "+NAME_2+" ================");
		
		/* Run all saved commands of the BDD part */
		for(Player p : Bukkit.getOnlinePlayers()) {
			List<String> commands = CommandsQuery.selectCommandsForPlayer(Database.getInstance().connect(), p);
			if(!commands.isEmpty()) {
				new PlayerCommandsExecutor(commands, p, false, p, new ActionInfo("", 0)).runPlayerCommands(true);
				CommandsQuery.deleteCommandsForPlayer(Database.getInstance().connect(), p);
			}
			if(SecurityOPQuery.selectIfSecurityOPcontains(Database.getInstance().connect(), p)) {
				p.setOp(false);
			}
		}

	}
	
	public void loadDependency() {
		/* Soft-Dependency part */
		if(Bukkit.getPluginManager().getPlugin("ExecutableItems")!=null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] ExecutableItems hooked !");
			hasExecutableItems = true;
		}
		
		if(Bukkit.getPluginManager().getPlugin("ExecutableBlocks")!=null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] ExecutableBlocks hooked !");
			hasExecutableBlocks = true;
		}
		
		if(Bukkit.getPluginManager().getPlugin("CustomPiglinsTrades")!=null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] CustomPiglinsTrades hooked !");
			hasCustomPiglinsTrades = true;
		}
		
		if(Bukkit.getPluginManager().getPlugin("SParkour")!=null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] SParkour hooked !");
			hasSParkour = true;
		}

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] PlaceholderAPI hooked !");
			hasPlaceholderAPI = true;
		}
		
		if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] WorldGuard hooked !");
			hasWorldGuard = true;
		}
		
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] Vault hooked !");
			hasVault = true;
		}
		if(Bukkit.getPluginManager().getPlugin("IridiumSkyblock")!=null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] IridiumSkyblock hooked !");
			hasIridiumSkyblock = true;
		}
	}

	@Override
	public void onDisable() {
		/* Save all delayed commands in BDD */
		HashMap<String,List<String>> saveCommands = CommandsManager.getInstance().getServerOffPlayerCommands();
		for(String playerName: saveCommands.keySet()) {
			Player player = Bukkit.getPlayer(playerName);
			for(String command: saveCommands.get(playerName)) {
				CommandsQuery.insertCommand(Database.getInstance().connect(), player, command);
			}
		}
	}

	public void onReload() {
		Utils.sendConsoleMsg("================ "+NAME_2+" ================");
		GeneralConfig.getInstance().reload();
		
		MessageMain.getInstance().load();

		MessageMain.getInstance().loadMessagesOf(plugin, MessageInterface.getMessagesEnum(Message.values()));

		Utils.sendConsoleMsg("================ "+NAME_2+" ================");
	}

	public static SCore getPlugin() {
		return plugin;
	}

	/* The server is in 1.12 ? */
	public static boolean is1v12() {
		return Bukkit.getServer().getVersion().contains("1.12");
	}

	/* The server is in 1.13 ? */
	public static boolean is1v13() {
		return Bukkit.getServer().getVersion().contains("1.13");
	}

	/* The server is in 1.14 ? */
	public static boolean is1v14() {
		return Bukkit.getServer().getVersion().contains("1.14");
	}

	/* The server is in 1.15 ? */
	public static boolean is1v15() {
		return Bukkit.getServer().getVersion().contains("1.15");
	}

	/* The server is in 1.16 ? */
	public static boolean is1v16() {
		return Bukkit.getServer().getVersion().contains("1.16");
	}

	/* The server is in 1.17 ? */
	public static boolean is1v17() {
		return Bukkit.getServer().getVersion().contains("1.17");
	}
	
	
}
