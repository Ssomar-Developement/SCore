package com.ssomar.score.linkedplugins;

import com.ssomar.executableitems.executableitems.ExecutableItem;
import com.ssomar.executableitems.executableitems.ExecutableItemsManager;
import com.ssomar.executableitems.executableitems.activators.ActivatorEI;
import org.bukkit.entity.Player;

import com.ssomar.executableblocks.blocks.ExecutableBlock;
import com.ssomar.executableblocks.blocks.ExecutableBlockManager;
import com.ssomar.executableblocks.blocks.activators.ActivatorEB;
import com.ssomar.executableblocks.menu.blocks.activators.ActivatorGUIManager;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import org.jetbrains.annotations.Nullable;

public class LinkedPlugins {

	@Nullable
	public static SObject getSObject(SPlugin sPlugin, String objectID) {

		switch(sPlugin.getName().toUpperCase()) {

		case "EXECUTABLEBLOCKS":
			if(SCore.hasExecutableBlocks) {
				return ExecutableBlockManager.getInstance().getLoadedObjectWithID(objectID).get();
			}
			break;
			
		case "EXECUTABLEITEMS":
			if(SCore.hasExecutableItems) {
				return ExecutableItemsManager.getInstance().getLoadedObjectWithID(objectID).get();
			}
			break;

		default:
			return null;
		}

		return null;
	}

	public static void reloadSObject(SPlugin sPlugin, String objectID) {

		switch(sPlugin.getName().toUpperCase()) {

		case "EXECUTABLEBLOCKS":
			if(SCore.hasExecutableBlocks) {
				ExecutableBlockManager.getInstance().reloadObject(objectID);
			}
			break;
			
		case "EXECUTABLEITEMS":
			if(SCore.hasExecutableItems) {
				ExecutableItemsManager.getInstance().reloadObject(objectID);
			}
			break;

		default:
			break;
		}

	}
	
	public static void openActivatorMenu(Player p,SPlugin sPlugin, SObject sObject, SActivator sActivator) {
		
		switch(sPlugin.getName().toUpperCase()) {

		case "EXECUTABLEBLOCKS":
			if(SCore.hasExecutableBlocks) {	
				ActivatorGUIManager.getInstance().startEditing(p, (ActivatorEB) sActivator, (ExecutableBlock) sObject);
			}
			break;
			
		case "EXECUTABLEITEMS":
			if(SCore.hasExecutableItems) {	
				com.ssomar.executableitems.configs.ingame.activators.ActivatorGUIManager.getInstance().startEditing(p, (ActivatorEI) sActivator, (ExecutableItem) sObject);
			}
			break;

		default:
			break;
		}
		
	}

}
