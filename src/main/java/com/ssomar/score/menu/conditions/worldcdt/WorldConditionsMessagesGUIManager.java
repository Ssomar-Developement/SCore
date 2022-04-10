package com.ssomar.score.menu.conditions.worldcdt;

import com.ssomar.score.menu.conditions.NewConditionsMessagesGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.sobject.sactivator.conditions.managers.WorldConditionsManager;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.entity.Player;


public class WorldConditionsMessagesGUIManager extends NewConditionsMessagesGUIManager<WorldConditionsMessagesGUI> {

	private static WorldConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, WorldConditions wC, String detail) {
		cache.put(p, new WorldConditionsMessagesGUI(sPlugin, sObject, sActivator, wC, detail));
		cache.get(p).openGUISync(p);
	}

	public static WorldConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new WorldConditionsMessagesGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		saveTheConfiguration(p, WorldConditionsManager.getInstance());
	}
}
