package com.ssomar.score.menu.conditions.worldcdt;

import com.ssomar.score.menu.conditions.NewConditionGUIAbstract;
import com.ssomar.score.menu.conditions.NewConditionMessagesGUIAbstract;
import com.ssomar.score.sobject.sactivator.conditions.managers.WorldConditionsManager;
import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.splugin.SPlugin;

public class WorldConditionsMessagesGUI extends NewConditionMessagesGUIAbstract {

	public WorldConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, WorldConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - World Conditions Messages", 3*9, sPlugin, sObject, sActivator, detail, conditions, WorldConditionsManager.getInstance());
	}

}
