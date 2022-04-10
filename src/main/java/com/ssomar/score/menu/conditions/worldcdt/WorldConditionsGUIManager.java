package com.ssomar.score.menu.conditions.worldcdt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ssomar.score.menu.conditions.NewConditionsGUIManagerAbstract;
import com.ssomar.score.sobject.sactivator.conditions.managers.WorldConditionsManager;
import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;


public class WorldConditionsGUIManager extends NewConditionsGUIManagerAbstract<WorldConditionsGUI> {

	private static WorldConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, WorldConditions wC, String detail) {
		cache.put(p, new WorldConditionsGUI(sPlugin, sObject, sActivator, wC, detail));
		cache.get(p).openGUISync(p);
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<WorldConditionsGUI> i) {
		String detail = cache.get(i.player).getDetail();
		this.saveTheConfiguration(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getId());
		WorldConditionsMessagesGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sObject.getActivator(i.sActivator.getID()).getWorldConditions(), detail);
	
		return true;
	}

	public static WorldConditionsGUIManager getInstance() {
		if(instance == null) instance = new WorldConditionsGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		saveTheConfiguration(p, WorldConditionsManager.getInstance());
	}
}

