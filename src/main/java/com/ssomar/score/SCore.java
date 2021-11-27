package com.ssomar.score;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.ssomar.score.projectiles.ProjectilesLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.CommandsClass;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageInterface;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.data.Database;
import com.ssomar.score.events.EventsHandler;
import com.ssomar.score.events.loop.LoopManager;
import com.ssomar.score.sobject.sactivator.cooldowns.CooldownsHandler;
import com.ssomar.score.utils.Utils;

public final class SCore extends JavaPlugin {

	public static SCore plugin;

	public static final String NAME = "SCore";

	public static final String NAME_2 = "[SCore]";

	private CommandsClass commandClass;

	public static boolean hasPlaceholderAPI = false;

	public static boolean hasExecutableItems = false;

	public static boolean hasExecutableBlocks = false;

	public static boolean hasCustomPiglinsTrades = false;

	public static boolean hasSParkour = false;

	public static boolean hasWorldGuard = false;

	public static boolean hasVault = false;

	public static boolean hasIridiumSkyblock = false;

	public static boolean hasMultiverse = false;

	public static boolean hasLands = false;

	public static boolean hasGriefPrevention = false;

	public static boolean hasGriefDefender = false;
	
	public static boolean hasCoreProtect = false;

	public static boolean hasProtocolLib = false;

	public static ProtocolManager protocolManager;

	@Override
	public void onEnable() {
		plugin = this;
		commandClass = new CommandsClass(this);

		Utils.sendConsoleMsg("================ "+NAME_2+" ================");

		this.loadDependency();

		GeneralConfig.getInstance();

		MessageMain.getInstance().load();

		MessageMain.getInstance().loadMessagesOf(plugin, MessageInterface.getMessagesEnum(Message.values()));

		/* Loop instance part */	
		LoopManager.getInstance().setup();

		ActionbarHandler.getInstance().load();

		/* Database */
		Database.getInstance().load();

		/* Events instance part */	
		EventsHandler.getInstance().setup(this);

		/* Commands part */
		this.getCommand("score").setExecutor(commandClass);

		/* Projectiles instance part */
		ProjectilesLoader.getInstance().load();

		Utils.sendConsoleMsg("================ "+NAME_2+" ================");

		CommandsHandler.getInstance().onEnable();
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
		if (Bukkit.getPluginManager().getPlugin("Multiverse-Core") != null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] Multiverse-Core hooked !");
			hasMultiverse = true;	
		}

		if (Bukkit.getPluginManager().getPlugin("Lands") != null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] Lands hooked !");
			hasLands = true;	
		}

		if (Bukkit.getPluginManager().getPlugin("GriefPrevention") != null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] GriefPrevention hooked !");
			hasGriefPrevention = true;	
		}

		if (Bukkit.getPluginManager().getPlugin("GriefDefender") != null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] GriefDefender hooked !");
			hasGriefDefender = true;	
		}
		
		if (Bukkit.getPluginManager().getPlugin("CoreProtect") != null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] CoreProtect hooked !");
			hasCoreProtect = true;	
		}

		if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] ProtocolLib hooked !");
			hasProtocolLib = true;
			/* Protocolib */
			protocolManager = ProtocolLibrary.getProtocolManager();
		}
	}

	@Override
	public void onDisable() {
		CommandsHandler.getInstance().onDisable();
		CooldownsHandler.closeServerSaveAll();
	}

	public void onReload() {
		Utils.sendConsoleMsg("================ "+NAME_2+" ================");
		GeneralConfig.getInstance().reload();

		/* Projectiles instance part */
		ProjectilesLoader.getInstance().reload();

		MessageMain.getInstance().loadMessagesOf(plugin, MessageInterface.getMessagesEnum(Message.values()));

		Utils.sendConsoleMsg("================ "+NAME_2+" ================");
	}

	public static SCore getPlugin() {
		return plugin;
	}

	public CommandsClass getCommandClass() {
		return commandClass;
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
	
	/* The server is in 1.16 ? */
	public static boolean is1v16v1() {
		return Bukkit.getServer().getVersion().contains("1.16.1");
	}

	/* The server is in 1.17 ? */
	public static boolean is1v17() {
		return Bukkit.getServer().getVersion().contains("1.17");
	}

	/* The server is in 1.18 ? */
	public static boolean is1v18() {
		return Bukkit.getServer().getVersion().contains("1.18");
	}


}
