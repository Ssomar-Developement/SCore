package com.ssomar.score.menu.activator.requiredei;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEIManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;


public class RequiredEIsGUIManager extends GUIManager<RequiredEIsGUI>{

	private static RequiredEIsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sAct) {
		cache.put(p, new RequiredEIsGUI(p, sPlugin, sObject, sAct));
		cache.get(p).openGUISync(p);
	}

	public void clicked(Player p, ItemStack item, String guiTitle) {
		if(item != null && item.hasItemMeta()) {
			SPlugin sPlugin = cache.get(p).getsPlugin();
			SObject sObject = cache.get(p).getSObject();
			SActivator sActivator = cache.get(p).getSAct();
			String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
			//String plName = sPlugin.getNameDesign();
			String currentPage = StringConverter.decoloredString(guiTitle);

			if(name.contains("Next page")) {
				new RequiredEIsGUI(Integer.parseInt(currentPage.split("Page ")[1])+1, p, sPlugin, sObject, sActivator).openGUISync(p);
			}
			else if(name.contains("Previous page")) {
				p.closeInventory();
				new RequiredEIsGUI(Integer.parseInt(currentPage.split("Page ")[1])-1, p, sPlugin, sObject, sActivator).openGUISync(p);
			}
			else if(name.contains("Return")) {
				RequiredEIGUIManager.getInstance().getCache().get(p).openGUISync(p);
			}
			else if(name.contains("Back")) {
				LinkedPlugins.openActivatorMenu(p, sPlugin, sObject, sActivator);
			}
			else if(name.contains("New RequiredEI")) {
				RequiredEIGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator);	
			}
			else {
				try {
					RequiredEIGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, sActivator.getRequiredEI(name.split("ID: ")[1]));
				}
				catch(Exception ignored) {}
			}

			cache.remove(p);
		}
	}

	public void shiftLeftClicked(Player p, ItemStack item, String guiTitle) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
		//String plName = sPlugin.getNameDesign();
		String currentPage = StringConverter.decoloredString(guiTitle);

		if(name.contains("Next page")) {
			new RequiredEIsGUI(Integer.parseInt(currentPage.split("Page ")[1])+1, p, sPlugin, sObject, sActivator).openGUISync(p);
		}
		else if(name.contains("Previous page")) {
			p.closeInventory();
			new RequiredEIsGUI(Integer.parseInt(currentPage.split("Page ")[1])-1, p, sPlugin, sObject, sActivator).openGUISync(p);
		}
		else if(name.contains("Return")) {
			RequiredEIGUIManager.getInstance().getCache().get(p).openGUISync(p);
		}
		else if(name.contains("Back")) {
			LinkedPlugins.openActivatorMenu(p, sPlugin, sObject, sActivator);
		}
		else if(name.contains("New RequiredEI")) {
			RequiredEIGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator);	
		}
		else if(!name.isEmpty()) {
			try {
				RequiredEIManager.deleteRequiredEI(sPlugin, sObject, sActivator, name.split("ID: ")[1]);
				LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
				sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
				sActivator = sObject.getActivator(sActivator.getID());
				cache.put(p, new RequiredEIsGUI(Integer.parseInt(currentPage.split("Page ")[1]), p, sPlugin, sObject, sActivator));
				cache.get(p).openGUISync(p);
			}
			catch(Exception ignored) {}
		}
	}

	public static RequiredEIsGUIManager getInstance() {
		if(instance == null) instance = new RequiredEIsGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {

	}
}
