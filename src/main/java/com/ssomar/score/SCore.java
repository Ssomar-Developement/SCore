package com.ssomar.score;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.plotsquared.core.PlotAPI;
import com.ssomar.executableblocks.ExecutableBlocks;
import com.ssomar.score.projectiles.ProjectilesLoader;
import com.ssomar.score.splugin.SPlugin;
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

public final class SCore extends JavaPlugin implements SPlugin {

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

	public static boolean hasTowny = false;

	public static boolean hasGriefPrevention = false;

	public static boolean hasGriefDefender = false;
	
	public static boolean hasCoreProtect = false;

	public static boolean hasProtocolLib = false;

	public static boolean hasPlotSquared = false;

	public static ProtocolManager protocolManager;

	@Override
	public void onEnable() {
		plugin = this;
		commandClass = new CommandsClass(this);

		Utils.sendConsoleMsg("================ "+NAME_2+" ================");

		this.initVersion();

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

		CommandsHandler.getInstance().onEnable();

		Utils.sendConsoleMsg("================ "+NAME_2+" ================");
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

		if (Bukkit.getPluginManager().getPlugin("Towny") != null) {
			SCore.plugin.getServer().getLogger().info("["+NAME+"] Towny hooked !");
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

		if (Bukkit.getPluginManager().getPlugin("PlotSquared") != null) {
			try{
				PlotAPI plotAPI = new PlotAPI();
				SCore.plugin.getServer().getLogger().info("["+NAME+"] PlotSquared hooked !");
				hasPlotSquared = true;
			}catch (NoClassDefFoundError e){
				ExecutableBlocks.plugin.getServer().getLogger().severe("["+NAME+"] PlotSquared hooked BUT you haven't the good version ! (try to update it) !");
				hasPlotSquared = false;
			}
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

	@Override
	public String getShortName() {
		return NAME;
	}

	@Override
	public String getNameDesign() {
		return NAME_2;
	}

	@Override
	public String getObjectName() {
		return null;
	}

	@Override
	public SCore getPlugin() {
		return plugin;
	}

	@Override
	public boolean isLotOfWork() {
		return false;
	}

	public CommandsClass getCommandClass() {
		return commandClass;
	}


	private static boolean is1v12 = false;
	private static boolean is1v13 = false;
	private static boolean is1v14 = false;
	private static boolean is1v15 = false;
	private static boolean is1v16 = false;
	private static boolean is1v16v1 = false;
	private static boolean is1v17 = false;
	private static boolean is1v18 = false;
	private static boolean is1v16Plus = false;
	private static boolean is1v17Plus = false;

	public void initVersion(){
		is1v12 = Bukkit.getServer().getVersion().contains("1.12");
		is1v13 = Bukkit.getServer().getVersion().contains("1.13");
		is1v14 = Bukkit.getServer().getVersion().contains("1.14");
		is1v15 = Bukkit.getServer().getVersion().contains("1.15");
		is1v16 = Bukkit.getServer().getVersion().contains("1.16");
		is1v16v1 = Bukkit.getServer().getVersion().contains("1.16.1");
		is1v17 = Bukkit.getServer().getVersion().contains("1.17");
		is1v18 = Bukkit.getServer().getVersion().contains("1.18");
	}



	/* The server is in 1.12 ? */
	public static boolean is1v12() {
		return is1v12;
	}

	/* The server is in 1.13 ? */
	public static boolean is1v13() {
		return is1v13;
	}

	/* The server is in 1.14 ? */
	public static boolean is1v14() {
		return is1v14;
	}

	/* The server is in 1.15 ? */
	public static boolean is1v15() {
		return is1v15;
	}

	/* The server is in 1.16 ? */
	public static boolean is1v16() {
		return is1v16;
	}
	
	/* The server is in 1.16 ? */
	public static boolean is1v16v1() {
		return is1v16v1;
	}

	/* The server is in 1.17 ? */
	public static boolean is1v17() {
		return is1v17;
	}

	/* The server is in 1.18 ? */
	public static boolean is1v18() {
		return is1v18;
	}

	/* The server is in 1.16 or + ? */
	public static boolean is1v16Plus() {
		return is1v16() || is1v17() || is1v18();
	}

	/* The server is in 1.17 or + ? */
	public static boolean is1v17Plus() {
		return is1v17() || is1v18();
	}
}
