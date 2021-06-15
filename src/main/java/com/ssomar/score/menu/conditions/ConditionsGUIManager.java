package com.ssomar.score.menu.conditions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;


public class ConditionsGUIManager extends GUIManager<ConditionsGUI>{
	
	private static ConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sAct) {
		cache.put(p, new ConditionsGUI(sPlugin, sObject, sAct));
		cache.get(p).openGUISync(p);
	}

	public void clicked(Player p, ItemStack item) {
		if(item != null) {
			if(item.hasItemMeta()) {
				SPlugin sPlugin = cache.get(p).getsPlugin();
				SObject sObject = cache.get(p).getSObject();
				SActivator sActivator = cache.get(p).getSAct();
				String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
				//String plName = sPlugin.getNameDesign();
				
				if(name.contains(ConditionsGUI.OWNER_CONDITIONS)) {
					PlayerConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, sActivator.getOwnerConditions(), "ownerConditions");
				}

				else if(name.contains(ConditionsGUI.PLAYER_CONDITIONS)) {
					PlayerConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, sActivator.getPlayerConditions(), "playerConditions");
				}
				
				else if(name.contains(ConditionsGUI.WORLD_CONDITIONS)) {
					WorldConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, sActivator.getWorldConditions(), "worldConditions");
				}
				
				else if(name.contains(ConditionsGUI.ENTITY_CONDITIONS)) {
					EntityConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, sActivator.getTargetEntityConditions(), "entityConditions");
				}
				
				else if(name.contains(ConditionsGUI.BLOCK_CONDITIONS)) {
					//BlockConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, sActivator.getBlockConditions(), "blockConditions");
				}
				
				else if(name.contains(ConditionsGUI.PLACEHOLDERS_CONDITIONS)) {
					PlaceholdersConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, sActivator.getPlaceholdersConditions(), "placeholdersConditions");
				}

				
				if(name.contains("Back")) {
					LinkedPlugins.openActivatorMenu(p, sPlugin, sObject, sActivator);
				}
				cache.remove(p);
			}
		}
	}

	public static ConditionsGUIManager getInstance() {
		if(instance == null) instance = new ConditionsGUIManager();
		return instance;
	}
}
