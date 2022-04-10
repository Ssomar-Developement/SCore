package com.ssomar.score.menu.conditions.blockcdt;

import com.ssomar.score.menu.conditions.NewConditionGUIAbstract;
import com.ssomar.score.menu.conditions.NewConditionMessagesGUIAbstract;
import com.ssomar.score.sobject.sactivator.conditions.condition.Condition;
import com.ssomar.score.sobject.sactivator.conditions.managers.BlockConditionsManager;
import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;

public class BlockConditionsMessagesGUI extends NewConditionMessagesGUIAbstract {
	
	public BlockConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Block Conditions Messages", 3*9, sPlugin, sObject, sActivator, detail, conditions, BlockConditionsManager.getInstance());
	}
}