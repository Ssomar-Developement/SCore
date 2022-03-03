package com.ssomar.score.splugin;

import org.bukkit.plugin.Plugin;

public interface SPlugin {

	String getShortName();
	
	String getName();
	
	String getNameDesign();

	String getObjectName();
	
	Plugin getPlugin();
	
	boolean isLotOfWork();
}
