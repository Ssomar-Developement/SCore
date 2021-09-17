package com.ssomar.score.splugin;

import org.bukkit.plugin.Plugin;

public interface SPlugin {

	String getShortName();
	
	String getName();
	
	String getNameDesign();
	
	Plugin getPlugin();
	
	boolean isLotOfWork();
}
