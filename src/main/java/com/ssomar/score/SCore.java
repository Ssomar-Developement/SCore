package com.ssomar.score;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.data.Database;
import com.ssomar.score.utils.Utils;

public final class SCore extends JavaPlugin {

	public static SCore plugin;
	
	public static final String NAME = "SCore";
	
	public static final String NAME_2 = "[SCore]";
	
	private static boolean hasPlaceholderAPI = false;
	
	private static boolean hasExecutableItems = false;
	
	private static boolean hasExecutableBlocks = false;
	
	private static boolean hasCustomPiglinsTrades = false;
	
	private static boolean hasSParkour = false;
	
	private static boolean hasWorldGuard = false;

	
	@Override
	public void onEnable() {
		plugin = this;

		Utils.sendConsoleMsg("================ "+NAME_2+" ================");
		
		this.loadDependency();

		GeneralConfig.getInstance();
		MessageMain.getInstance().load();
		

		/* Database */
		Database.getInstance().load();
		


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
	}

	@Override
	public void onDisable() {}

	public void onReload() {
		Utils.sendConsoleMsg("================ "+NAME_2+" ================");
		GeneralConfig.getInstance().reload();

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

	/* The server has PlaceholderAPI ? */
	public static boolean hasPlaceholderAPI() {
		return hasPlaceholderAPI;
	}

	public static boolean isHasPlaceholderAPI() {
		return hasPlaceholderAPI;
	}

	public static void setHasPlaceholderAPI(boolean hasPlaceholderAPI) {
		SCore.hasPlaceholderAPI = hasPlaceholderAPI;
	}

	public static boolean isHasExecutableItems() {
		return hasExecutableItems;
	}

	public static void setHasExecutableItems(boolean hasExecutableItems) {
		SCore.hasExecutableItems = hasExecutableItems;
	}

	public static boolean isHasExecutableBlocks() {
		return hasExecutableBlocks;
	}

	public static void setHasExecutableBlocks(boolean hasExecutableBlocks) {
		SCore.hasExecutableBlocks = hasExecutableBlocks;
	}

	public static boolean isHasCustomPiglinsTrades() {
		return hasCustomPiglinsTrades;
	}

	public static void setHasCustomPiglinsTrades(boolean hasCustomPiglinsTrades) {
		SCore.hasCustomPiglinsTrades = hasCustomPiglinsTrades;
	}

	public static boolean isHasSParkour() {
		return hasSParkour;
	}

	public static void setHasSParkour(boolean hasSParkour) {
		SCore.hasSParkour = hasSParkour;
	}

	public static boolean isHasWorldGuard() {
		return hasWorldGuard;
	}

	public static void setHasWorldGuard(boolean hasWorldGuard) {
		SCore.hasWorldGuard = hasWorldGuard;
	}

	
	
}
