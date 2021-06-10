package com.ssomar.score.linkedplugins;

import javax.annotation.Nullable;

import org.bukkit.plugin.Plugin;

import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;

public class LinkedPlugins {
	
	@Nullable
	public static SObject getSObject(Plugin plugin, String objectID) {
		
		switch(plugin.getName().toUpperCase()) {
		
		case "EXECUTABLEBLOCKS":
			if(SCore.hasExecutableBlocks) {
				//return ExecutableBlockManager.getInstance().getLoadedBlockWithID(objectID);
			}
			break;
			
		default:
			return null;
		}
		
		return null;
	}

}
