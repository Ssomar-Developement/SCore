package com.ssomar.score.menu.conditions.home;

import com.ssomar.score.conditions.managers.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;


public class ConditionsHomeGUIManager extends GUIManager<ConditionsHomeGUI>{

	private static ConditionsHomeGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sAct) {
		cache.put(p, new ConditionsHomeGUI(sPlugin, sObject, sAct));
		cache.get(p).openGUISync(p);
	}

	public void clicked(Player p, ItemStack item) {
		if(item != null && item.hasItemMeta()) {
			SPlugin sPlugin = cache.get(p).getsPlugin();
			SObject sObject = cache.get(p).getSObject();
			SActivator sActivator = cache.get(p).getSAct();
			String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
			//String plName = sPlugin.getNameDesign();

			if(name.contains(ConditionsHomeGUI.OWNER_CONDITIONS)) {
				com.ssomar.score.menu.conditions.general.ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, "ownerConditions", sActivator.getOwnerConditions(), PlayerConditionsManager.getInstance());
			}

			else if(name.contains(ConditionsHomeGUI.PLAYER_CONDITIONS)) {
				com.ssomar.score.menu.conditions.general.ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, "playerConditions", sActivator.getPlayerConditions(), PlayerConditionsManager.getInstance());
			}

			else if(name.contains(ConditionsHomeGUI.TARGET_CONDITIONS)) {
				com.ssomar.score.menu.conditions.general.ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator,"targetConditions", sActivator.getTargetPlayerConditions(), PlayerConditionsManager.getInstance());
			}

			else if(name.contains(ConditionsHomeGUI.WORLD_CONDITIONS)) {
				com.ssomar.score.menu.conditions.general.ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator,"worldConditions", sActivator.getWorldConditions(), WorldConditionsManager.getInstance());
			}

			else if(name.contains(ConditionsHomeGUI.ITEM_CONDITIONS)) {
				com.ssomar.score.menu.conditions.general.ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, "itemConditions", sActivator.getItemConditions(), ItemConditionsManager.getInstance());
			}

			else if(name.contains(ConditionsHomeGUI.ENTITY_CONDITIONS)) {
				com.ssomar.score.menu.conditions.general.ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, "entityConditions", sActivator.getTargetEntityConditions(), EntityConditionsManager.getInstance());
			}

			else if(name.contains(ConditionsHomeGUI.BLOCK_CONDITIONS)) {
				com.ssomar.score.menu.conditions.general.ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, "blockConditions", sActivator.getBlockConditions(), BlockConditionsManager.getInstance());
			}

			else if(name.contains(ConditionsHomeGUI.TARGET_BLOCK_CONDITIONS)) {
				com.ssomar.score.menu.conditions.general.ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, "targetBlockConditions", sActivator.getTargetBlockConditions(), BlockConditionsManager.getInstance());
			}

			/*else if(name.contains(ConditionsGUI.PLACEHOLDERS_CONDITIONS)) {
				PlaceholdersConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, sActivator.getPlaceholdersConditions(), "placeholdersConditions");
			}*/

			else if(name.contains(ConditionsHomeGUI.CUSTOM_EI_CONDITIONS)) {
				com.ssomar.score.menu.conditions.general.ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, "customConditions", sActivator.getCustomEIConditions(), CustomEIConditionsManager.getInstance());
			}


			if(name.contains("Back")) {
				LinkedPlugins.openActivatorMenu(p, sPlugin, sObject, sActivator);
			}
			cache.remove(p);
		}
	}

	public static ConditionsHomeGUIManager getInstance() {
		if(instance == null) instance = new ConditionsHomeGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {

	}
}
