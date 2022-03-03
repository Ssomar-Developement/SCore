package com.ssomar.score.menu.conditions.home;

import org.bukkit.Material;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.SOption;
import com.ssomar.score.splugin.SPlugin;

public class ConditionsGUI extends ConditionGUIAbstract{
	
	public static final String OWNER_CONDITIONS = "Owner Conditions";
	public static final String PLAYER_CONDITIONS = "Player Conditions";
	public static final String TARGET_CONDITIONS = "Target Conditions";
	public static final String WORLD_CONDITIONS = "World Conditions";
	public static final String ITEM_CONDITIONS = "Item Conditions";
	public static final String ENTITY_CONDITIONS = "Entity Conditions";
	public static final String BLOCK_CONDITIONS = "Block Conditions";
	public static final String TARGET_BLOCK_CONDITIONS = "Target block Conditions";
	public static final String PLACEHOLDERS_CONDITIONS = "Placeholders Conditions";
	public static final String CUSTOM_EI_CONDITIONS = "Custom EI Conditions";

	public ConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sAct) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Conditions", 3*9, sPlugin, sObject, sAct, "null", null);
	}

	@Override
	public void loadTheGUI() {
		SOption sOp = this.getSAct().getOption();
		int i = 0;
		
		if(sOp.getOptionWithOwner().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+OWNER_CONDITIONS, 	false,	false, "&7&oThe owner condtions", "&a✎ Click here to change");
			i++;
		}

		if(sOp.getOptionWithPlayer().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+PLAYER_CONDITIONS, 	false,	false, "&7&oThe player condtions", "&a✎ Click here to change");
			i++;
		}
		
		if(sOp.getOptionWithTargetPlayer().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+TARGET_CONDITIONS, 	false,	false, "&7&oThe target player condtions", "&a✎ Click here to change");
			i++;
		}

		if(sOp.getOptionWithWorld().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+WORLD_CONDITIONS, 	false,	false, "&7&oThe world condtions", "&a✎ Click here to change");
			i++;
		}
		
		if(sOp.getOptionWithItem().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+ITEM_CONDITIONS, 	false,	false, "&7&oThe item condtions", "&a✎ Click here to change");
			i++;
		}

		if(sOp.getOptionWithTargetEntity().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+ENTITY_CONDITIONS, 	false,	false, "&7&oThe entity condtions", "&a✎ Click here to change");
			i++;
		}
		
		if(sOp.getOptionWithBlock().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+BLOCK_CONDITIONS, 	false,	false, "&7&oThe block condtions", "&a✎ Click here to change");
			i++;
		}
		
		if(sOp.getOptionWithTargetBlock().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+TARGET_BLOCK_CONDITIONS, 	false,	false, "&7&oThe target block condtions", "&a✎ Click here to change");
			i++;
		}
		
		if(sOp.getOptionWithOwner().contains(sOp) || sOp.getOptionWithPlayer().contains(sOp)) {
			if(SCore.hasExecutableBlocks) createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+PLACEHOLDERS_CONDITIONS, 	false,	false, "&7&oThe placeholders condtions", "&6For EB: &eowner = &aplayer &6& &etarget player = &atarget", "&a✎ Click here to change");
			else createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+PLACEHOLDERS_CONDITIONS, 	false,	false, "&7&oThe placeholders condtions", "&a✎ Click here to change");
			i++;
		}
		
		if(this.getsPlugin().getShortName().equalsIgnoreCase("ei") && sOp.getOptionWithPlayer().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+CUSTOM_EI_CONDITIONS, 	false,	false, "&7&oThe custom condtions", "&a✎ Click here to change");
			i++;
		}

		createItem(RED, 									1 , 18, 	"&4&l▶ &cBack to activator config", 	false, false);

		createItem(Material.BOOK, 							1 , 24, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getId());
		createItem(Material.BOOK, 							1 , 25, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());

		return;
	}
}
