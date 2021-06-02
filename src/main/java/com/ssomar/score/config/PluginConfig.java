package com.ssomar.score.config;

import org.bukkit.plugin.Plugin;

public class PluginConfig {
	
	private Plugin plugin;
	
	private String name;

	public Plugin getPlugin() {
		return plugin;
	}

	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
