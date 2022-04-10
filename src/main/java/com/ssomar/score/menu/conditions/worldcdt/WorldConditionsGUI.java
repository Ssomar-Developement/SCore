package com.ssomar.score.menu.conditions.worldcdt;

import java.util.List;

import com.ssomar.score.menu.conditions.NewConditionGUIAbstract;
import com.ssomar.score.sobject.sactivator.conditions.managers.WorldConditionsManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.splugin.SPlugin;

public class WorldConditionsGUI extends NewConditionGUIAbstract {
	
	public WorldConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, WorldConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - World Conditions", 3*9, sPlugin, sObject, sActivator, detail, conditions, WorldConditionsManager.getInstance());
	}
}
