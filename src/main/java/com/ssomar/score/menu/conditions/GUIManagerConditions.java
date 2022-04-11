package com.ssomar.score.menu.conditions;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.menu.conditions.home.ConditionsGUIManager;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;

public abstract class GUIManagerConditions<T extends GUIAbstract> extends GUIManagerSCore<T> {

	public <X extends ConditionGUIAbstract> boolean saveOrBackOrNothing(InteractionClickedGUIManager<X> i) {
		if(i.name.contains("Save")) {
			this.saveTheConfiguration(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getId());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
		}
		else return false;
		
		return true;
	}

	public <X extends NewConditionsGUIAbstract> boolean saveOrBackOrNothingNEW(InteractionClickedGUIManager<X> i) {
		if(i.name.contains("Save")) {
			this.saveTheConfiguration(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getId());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
		}
		else return false;

		return true;
	}

}
