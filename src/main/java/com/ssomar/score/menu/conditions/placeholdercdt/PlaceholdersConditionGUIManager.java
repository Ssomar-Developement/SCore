package com.ssomar.score.menu.conditions.placeholdercdt;

import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.Comparator;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCdtType;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCondition;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.NTools;
import com.ssomar.score.utils.StringConverter;

public class PlaceholdersConditionGUIManager extends GUIManagerSCore<PlaceholdersConditionGUI>{

	private static PlaceholdersConditionGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, List<PlaceholdersCondition> list, String detail) {
		cache.put(p, new PlaceholdersConditionGUI(sPlugin, sObject, sActivator, list, detail));
		cache.get(p).openGUISync(p);
	}

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, PlaceholdersCondition pC, String detail) {
		cache.put(p, new PlaceholdersConditionGUI(sPlugin, sObject, sActivator, pC, detail));
		cache.get(p).openGUISync(p);
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


	public static PlaceholdersConditionGUIManager getInstance() {
		if(instance == null) instance = new PlaceholdersConditionGUIManager();
		return instance;
	}

	@Override
	public boolean allClicked(InteractionClickedGUIManager<PlaceholdersConditionGUI> i) {
		PlaceholdersConditionGUI gui = cache.get(i.player);

		if(i.name.contains(PlaceholdersConditionGUI.TYPE)) gui.changeType();

		else if(i.name.contains(PlaceholdersConditionGUI.COMPARATOR)) gui.changeComparator();

		else if(i.name.contains(PlaceholdersConditionGUI.CANCEL_EVENT)) gui.changeBoolean(PlaceholdersConditionGUI.CANCEL_EVENT);

		else if(i.name.contains(PlaceholdersConditionGUI.PART1)) {
			requestWriting.put(i.player, PlaceholdersConditionGUI.PART1);
			i.player.closeInventory();
			space(i.player);

			String message = StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &aEnter your first part: (example: %player_health%)");
			i.player.sendMessage(message);
			space(i.player);
		}

		else if(i.name.contains(PlaceholdersConditionGUI.PART2)) {
			requestWriting.put(i.player, PlaceholdersConditionGUI.PART2);
			i.player.closeInventory();
			space(i.player);

			String message = StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &aEnter your second part: (example: 5, or %player_health%, or TEAM_BLUE)");
			i.player.sendMessage(message);
			space(i.player);
		}

		else if(i.name.contains(PlaceholdersConditionGUI.MESSAGE)) {
			requestWriting.put(i.player, PlaceholdersConditionGUI.MESSAGE);
			i.player.closeInventory();
			space(i.player);

			String message = StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &aEnter your not valid message here:");
			i.player.sendMessage(message);
			space(i.player);
		}
		if(i.name.contains("Save") || i.name.contains("Create this Placeholders Condition")) {
			try {
				cache.get(i.player).getActually(PlaceholdersConditionGUI.PART1);
				cache.get(i.player).getActually(PlaceholdersConditionGUI.PART2);
			}
			catch(ArrayIndexOutOfBoundsException e) {
				String message = StringConverter.coloredString("&4&l"+i.sPlugin.getNameDesign()+" &cERROR the first part and the second part can't be empty !");
				i.player.sendMessage(message);
				return true;
			}

			if(PlaceholdersCdtType.getpCdtTypeWithNumber().contains(cache.get(i.player).getType()) && !NTools.isNumber(cache.get(i.player).getActually(PlaceholdersConditionGUI.PART2))){
				String message = StringConverter.coloredString("&4&l"+i.sPlugin.getNameDesign()+" &cERROR the second par must be a number");
				i.player.sendMessage(message);
				return true;
			}

			String detail = cache.get(i.player).getDetail();
			this.saveTheConfiguration(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			i.sActivator = i.sObject.getActivator(i.sActivator.getID());
			PlaceholdersConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sActivator.getPlaceholdersConditions(), detail);
		}

		else if(i.name.contains("Back")) {
			PlaceholdersConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sActivator.getPlaceholdersConditions(), cache.get(i.player).getDetail());
		}
		else return false;
		
		return true;
	}

	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<PlaceholdersConditionGUI> i) {
		return false;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<PlaceholdersConditionGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<PlaceholdersConditionGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<PlaceholdersConditionGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<PlaceholdersConditionGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<PlaceholdersConditionGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<PlaceholdersConditionGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<PlaceholdersConditionGUI> interact) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void saveTheConfiguration(Player p) {
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
}