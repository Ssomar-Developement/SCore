package com.ssomar.score.menu.conditions.blockcdt.blockaroundcdt;

import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.conditions.blockcdt.BlockConditionsGUIManager;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.AroundBlockCondition;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class AroundBlockConditionsGUIManager extends GUIManagerConditions<AroundBlockConditionsGUI>{

	private static AroundBlockConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, List<AroundBlockCondition> list, String detail) {
		cache.put(p, new AroundBlockConditionsGUI(sPlugin, sObject, sActivator, list, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> i) {
		if(!i.name.isEmpty()){
			AroundBlockCondition aBC = null;
			for (AroundBlockCondition place :  cache.get(i.player).getList()) {
				if (place.getId().equals(StringConverter.decoloredString(i.name).split("✦ ID: ")[1]))
					aBC = place;
			}
			if (aBC != null) {
				AroundBlockConditionGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, aBC, cache.get(i.player).getDetail());
				cache.remove(i.player);
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> i) {
		String cPage  = StringConverter.decoloredString(i.title);
		
		if (i.name.contains("Next page")) {
			cache.replace(i.player, new AroundBlockConditionsGUI(Integer.valueOf(cPage.split("Page ")[1]) + 1, i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getList(), cache.get(i.player).getDetail()));
			cache.get(i.player).openGUISync(i.player);
		} else if (i.name.contains("Previous page")) {
			cache.replace(i.player, new AroundBlockConditionsGUI(Integer.valueOf(cPage.split("Page ")[1]) - 1, i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getList(), cache.get(i.player).getDetail()));
			cache.get(i.player).openGUISync(i.player);
		}
		else if (i.name.contains("New Around block cdt")) {
			i.player.closeInventory();
			AroundBlockConditionGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getList(), cache.get(i.player).getDetail());
		} 
		
		else if(i.name.contains("Back")) {
			BlockConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sActivator.getBlockConditions(), cache.get(i.player).getDetail());
		}
		else return false;
		
		return true;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> i) {
		String cPage  = StringConverter.decoloredString(i.title);
		try {
			String id = i.name.split("✦ ID: ")[1];
			AroundBlockCondition.deleteBACCdt(i.sPlugin, i.sObject, i.sActivator, id, cache.get(i.player).getDetail());
			LinkedPlugins.reloadSObject(i.sPlugin, i.sObject.getID());
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			i.sActivator = i.sObject.getActivator(i.sActivator.getID());
			cache.replace(i.player, new AroundBlockConditionsGUI(Integer.valueOf(cPage.split("Page ")[1]), i.sPlugin, i.sObject, i.sActivator, i.sActivator.getBlockConditions().getBlockAroundConditions(), cache.get(i.player).getDetail()));
			cache.get(i.player).openGUISync(i.player);
		}
		catch(Exception e) {

		}
		
		return true;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> interact) {
		return false;
	}


	public static AroundBlockConditionsGUIManager getInstance() {
		if(instance == null) instance = new AroundBlockConditionsGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		// TODO Auto-generated method stub
		
	}
}
