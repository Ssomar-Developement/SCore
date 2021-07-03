package com.ssomar.score.menu.conditions.placeholdercdt;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.Comparator;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCdtType;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCondition;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.NTools;
import com.ssomar.score.utils.StringConverter;

public class PlaceholdersConditionGUIManager extends GUIManager<PlaceholdersConditionGUI>{

	private static PlaceholdersConditionGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, List<PlaceholdersCondition> list, String detail) {
		cache.put(p, new PlaceholdersConditionGUI(sPlugin, sObject, sActivator, list, detail));
		cache.get(p).openGUISync(p);
	}

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, PlaceholdersCondition pC, String detail) {
		cache.put(p, new PlaceholdersConditionGUI(sPlugin, sObject, sActivator, pC, detail));
		cache.get(p).openGUISync(p);
	}

	public void clicked(Player p, ItemStack item) {
		if(item != null && item.hasItemMeta()) {
			SPlugin sPlugin = cache.get(p).getsPlugin();
			SObject sObject = cache.get(p).getSObject();
			SActivator sAct = cache.get(p).getSAct();
			String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
			String plName = sPlugin.getNameDesign();

			PlaceholdersConditionGUI gui = cache.get(p);

			if(name.contains(PlaceholdersConditionGUI.TYPE)) gui.changeType();

			else if(name.contains(PlaceholdersConditionGUI.COMPARATOR)) gui.changeComparator();

			else if(name.contains(PlaceholdersConditionGUI.CANCEL_EVENT)) gui.changeBoolean(PlaceholdersConditionGUI.CANCEL_EVENT);

			else if(name.contains(PlaceholdersConditionGUI.PART1)) {
				requestWriting.put(p, PlaceholdersConditionGUI.PART1);
				p.closeInventory();
				space(p);

				String message = StringConverter.coloredString("&a&l"+plName+" &aEnter your first part: (example: %player_health%)");
				p.sendMessage(message);
				space(p);
			}

			else if(name.contains(PlaceholdersConditionGUI.PART2)) {
				requestWriting.put(p, PlaceholdersConditionGUI.PART2);
				p.closeInventory();
				space(p);

				String message = StringConverter.coloredString("&a&l"+plName+" &aEnter your second part: (example: 5, or %player_health%, or TEAM_BLUE)");
				p.sendMessage(message);
				space(p);
			}

			else if(name.contains(PlaceholdersConditionGUI.MESSAGE)) {
				requestWriting.put(p, PlaceholdersConditionGUI.MESSAGE);
				p.closeInventory();
				space(p);

				String message = StringConverter.coloredString("&a&l"+plName+" &aEnter your not valid message here:");
				p.sendMessage(message);
				space(p);
			}

			else if(name.contains("Reset")) {
				p.closeInventory();
				cache.replace(p, new PlaceholdersConditionGUI(sPlugin, sObject, sAct, new PlaceholdersCondition(cache.get(p).getActually(PlaceholdersConditionGUI.CDT_ID)), cache.get(p).getDetail()));
				cache.get(p).openGUISync(p);
			}

			else if(name.contains("Save") || name.contains("Create this Placeholders Condition")) {
				try {
					cache.get(p).getActually(PlaceholdersConditionGUI.PART1);
					cache.get(p).getActually(PlaceholdersConditionGUI.PART2);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					String message = StringConverter.coloredString("&4&l"+plName+" &cERROR the first part and the second part can't be empty !");
					p.sendMessage(message);
					return;
				}

				if(PlaceholdersCdtType.getpCdtTypeWithNumber().contains(cache.get(p).getType()) && !NTools.isNumber(cache.get(p).getActually(PlaceholdersConditionGUI.PART2))){
					String message = StringConverter.coloredString("&4&l"+plName+" &cERROR the second par must be a number");
					p.sendMessage(message);
					return;
				}

				String detail = cache.get(p).getDetail();
				savePlaceholdersCondition(p);
				sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
				sAct = sObject.getActivator(sAct.getID());
				PlaceholdersConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sAct.getPlaceholdersConditions(), detail);
			}

			else if(name.contains("Exit")) {
				p.closeInventory();
			}

			else if(name.contains("Back")) {
				PlaceholdersConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sAct.getPlaceholdersConditions(), cache.get(p).getDetail());
			}
		}
	}

	public void receivedMessage(Player p, String message) {
		switch(requestWriting.get(p)) {

		case PlaceholdersConditionGUI.PART1:
			cache.get(p).updateActually(PlaceholdersConditionGUI.PART1, message);
			break;
		case PlaceholdersConditionGUI.PART2:
			cache.get(p).updateActually(PlaceholdersConditionGUI.PART2, message);
			break;
		case PlaceholdersConditionGUI.MESSAGE:
			cache.get(p).updateActually(PlaceholdersConditionGUI.MESSAGE, StringConverter.coloredString(message));
			break;
		default:
			break;
		}

		cache.get(p).openGUISync(p);
		requestWriting.remove(p);
	}

	public void savePlaceholdersCondition(Player p) {

		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();

		String id = cache.get(p).getActually(PlaceholdersConditionGUI.CDT_ID);
		PlaceholdersCdtType type = cache.get(p).getType();
		String message = cache.get(p).getActuallyWithColor(PlaceholdersConditionGUI.MESSAGE);
		String part1 = cache.get(p).getActually(PlaceholdersConditionGUI.PART1);
		String part2 = cache.get(p).getActually(PlaceholdersConditionGUI.PART2);
		Comparator comp = cache.get(p).getComparator();
		PlaceholdersCondition pC;
		if(type.equals(PlaceholdersCdtType.PLAYER_NUMBER) || type.equals(PlaceholdersCdtType.TARGET_NUMBER)) {
			pC = new PlaceholdersCondition(id, type, message, part1, comp, Double.valueOf(part2));
		}
		else pC = new PlaceholdersCondition(id, type, message, part1, comp, part2);
		pC.setCancelEvent(cache.get(p).getBoolean(PlaceholdersConditionGUI.CANCEL_EVENT));

		PlaceholdersCondition.savePlaceholdersCdt(sPlugin, sObject, sActivator, pC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static PlaceholdersConditionGUIManager getInstance() {
		if(instance == null) instance = new PlaceholdersConditionGUIManager();
		return instance;
	}
}