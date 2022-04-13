package com.ssomar.score.menu.conditions.blockaroundcdt;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockCondition;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockConditions;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.conditions.general.ConditionsGUIManager;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.entity.Player;

public class AroundBlockConditionsGUIManager extends GUIManagerSCore<AroundBlockConditionsGUI>{

	private static AroundBlockConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, AroundBlockConditions condition, String detail) {
		cache.put(p, new AroundBlockConditionsGUI(sPlugin, sObject, sActivator, conditionsManager, conditions, condition, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> i) {
		if(!i.name.isEmpty()){
			AroundBlockCondition aBC = null;
			AroundBlockConditionsGUI gui = cache.get(i.player);
			for (AroundBlockCondition place :  gui.getCondition().getCondition()) {
				try {
				if (place.getId().equals(StringConverter.decoloredString(i.name).split("✦ ID: ")[1]))
					aBC = place;
				}catch(ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
			if (aBC != null) {
				AroundBlockConditionGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), aBC, gui.getDetail());
				cache.remove(i.player);
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> i) {
		String cPage  = StringConverter.decoloredString(i.title);
		AroundBlockConditionsGUI gui = cache.get(i.player);
		if (i.name.contains("Next page")) {
			cache.replace(i.player, new AroundBlockConditionsGUI(Integer.parseInt(cPage.split("Page ")[1]) + 1, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), gui.getDetail()));
			cache.get(i.player).openGUISync(i.player);
		} else if (i.name.contains("Previous page")) {
			cache.replace(i.player, new AroundBlockConditionsGUI(Integer.parseInt(cPage.split("Page ")[1]) - 1, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), gui.getDetail()));
			cache.get(i.player).openGUISync(i.player);
		}
		else if (i.name.contains("New Around block cdt")) {
			i.player.closeInventory();
			AroundBlockConditionGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), gui.getDetail());
		} 
		
		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, gui.getDetail(), gui.getConditions(), gui.getConditionsManager());
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

			AroundBlockConditionsGUI gui = cache.get(i.player);
			gui.getCondition().removeCondition(id);
			cache.get(i.player).clear();
			cache.get(i.player).loadCdts();
		}
		catch(Exception ignored) {}
		
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

	}
}
