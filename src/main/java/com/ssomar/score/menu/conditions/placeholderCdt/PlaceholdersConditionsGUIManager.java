package com.ssomar.score.menu.conditions.placeholderCdt;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCondition;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;


public class PlaceholdersConditionsGUIManager extends GUIManager<PlaceholdersConditionsGUI>{
	
	private static PlaceholdersConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, List<PlaceholdersCondition> list, String detail) {
		cache.put(p, new PlaceholdersConditionsGUI(sPlugin, sObject, sActivator, list, detail));
		cache.get(p).openGUISync(p);
	}

	public void clicked(Player p, ItemStack item, String title) {
		String cPage  = StringConverter.decoloredString(title);
		if(item != null) {
			if(item.hasItemMeta()) {
				SPlugin sPlugin = cache.get(p).getsPlugin();
				SObject sObject = cache.get(p).getSObject();
				SActivator sActivator = cache.get(p).getSAct();
				String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
				String plName = sPlugin.getNameDesign();
				
				if (name.contains("Next page")) {
					cache.replace(p, new PlaceholdersConditionsGUI(Integer.valueOf(cPage.split("Page ")[1]) + 1, sPlugin, sObject, sActivator, cache.get(p).getList(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				} else if (name.contains("Previous page")) {
					cache.replace(p, new PlaceholdersConditionsGUI(Integer.valueOf(cPage.split("Page ")[1]) - 1, sPlugin, sObject, sActivator, cache.get(p).getList(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}
				else if (name.contains("New Placeholders cdt")) {
					p.closeInventory();
					PlaceholdersConditionGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, cache.get(p).getList(), cache.get(p).getDetail());
				} 
				else if(name.contains("Back")) {
					ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator);
				}
				else if(name.isEmpty()){
					return;
				}
				else {
					PlaceholdersCondition pC = null;
					for (PlaceholdersCondition place :  cache.get(p).getList()) {
						if (place.getId().equals(StringConverter.decoloredString(name).split("✦ ID: ")[1]))
							pC = place;
					}
					if (pC != null)
						PlaceholdersConditionGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, pC, cache.get(p).getDetail());
					else {
						p.sendMessage(StringConverter.coloredString(
								"&4&l"+plName+" &cCan't load this placeholder cdt, pls contact the developper on discord if you see this message"));
						PlaceholdersConditionGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, cache.get(p).getList(), cache.get(p).getDetail());
					}
				}

				
				cache.remove(p);
			}
		}
	}

	public void shiftLeftClicked(Player p, ItemStack item, String title) {
		String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
		String cPage  = StringConverter.decoloredString(title);
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		try {
			String id = name.split("✦ ID: ")[1];
			PlaceholdersCondition.deletePlaceholdersCdt(sPlugin, sObject, sActivator, id, cache.get(p).getDetail());
			LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
			sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
			sActivator = sObject.getActivator(sActivator.getID());
			cache.replace(p, new PlaceholdersConditionsGUI(Integer.valueOf(cPage.split("Page ")[1]), sPlugin, sObject, sActivator, sActivator.getPlaceholdersConditions(), cache.get(p).getDetail()));
			cache.get(p).openGUISync(p);
		}
		catch(Exception e) {
			
		}
		
	}
	
	public static PlaceholdersConditionsGUIManager getInstance() {
		if(instance == null) instance = new PlaceholdersConditionsGUIManager();
		return instance;
	}
}
