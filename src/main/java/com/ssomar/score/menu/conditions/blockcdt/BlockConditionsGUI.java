package com.ssomar.score.menu.conditions.blockcdt;

import com.ssomar.score.menu.conditions.NewConditionGUIAbstract;
import com.ssomar.score.sobject.sactivator.conditions.condition.Condition;
import com.ssomar.score.sobject.sactivator.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.sobject.sactivator.conditions.managers.BlockConditionsManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;

public class BlockConditionsGUI extends NewConditionGUIAbstract<BlockConditions, BlockCondition, BlockConditionsManager> {

	public BlockConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Block Conditions", 3*9, sPlugin, sObject, sActivator, detail, conditions, BlockConditionsManager.getInstance());
	}
}
