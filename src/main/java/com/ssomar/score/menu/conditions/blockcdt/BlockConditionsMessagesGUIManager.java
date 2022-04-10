package com.ssomar.score.menu.conditions.blockcdt;

import com.ssomar.score.menu.conditions.NewConditionsMessagesGUIManager;
import com.ssomar.score.sobject.sactivator.conditions.condition.Condition;
import com.ssomar.score.sobject.sactivator.conditions.managers.BlockConditionsManager;
import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;

import java.util.Optional;


public class BlockConditionsMessagesGUIManager extends NewConditionsMessagesGUIManager<BlockConditionsMessagesGUI> {

	private static BlockConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions bC, String detail) {
		cache.put(p, new BlockConditionsMessagesGUI(sPlugin, sObject, sActivator, bC, detail));
		cache.get(p).openGUISync(p);
	}

	public static BlockConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new BlockConditionsMessagesGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		saveTheConfiguration(p, BlockConditionsManager.getInstance());
	}
}
