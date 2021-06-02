package com.ssomar.score;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.utils.Utils;

public final class SCore extends JavaPlugin {

	public static SCore plugin;

	private static boolean hasPlaceholderAPI= false;
	
	public static final String NAME = "SCore";
	public static final String NAME_2 = "[SCore]";

	
	@Override
	public void onEnable() {
		plugin = this;

		Utils.sendConsoleMsg("================ "+NAME_2+" ================");

		GeneralConfig.getInstance();
		MessageMain.getInstance().load();


		Utils.sendConsoleMsg("================ "+NAME_2+" ================");

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
}
