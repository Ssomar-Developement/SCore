package com.ssomar.score.sobject.sactivator.menu.conditions;

import org.bukkit.Material;

import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.SOption;
import com.ssomar.score.splugin.SPlugin;

public class ConditionsGUI extends ConditionGUIAbstract{
	
	public static final String OWNER_CONDITIONS = "Owner Conditions";
	public static final String PLAYER_CONDITIONS = "Player Conditions";
	public static final String WORLD_CONDITIONS = "World Conditions";
	public static final String ENTITY_CONDITIONS = "Entity Conditions";
	public static final String BLOCK_CONDITIONS = "Block Conditions";
	public static final String PLACEHOLDERS_CONDITIONS = "Placeholders Conditions";

	public ConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sAct) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Conditions", 3*9, sPlugin, sObject, sAct, "null");

		SOption sOp = sAct.getOption();
		int i=0;
		
		if(sOp.getOptionWithOwner().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+OWNER_CONDITIONS, 	false,	false, "&7&oThe owner condtions", "&a✎ Click here to change");
			i++;
		}

		if(sOp.getOptionWithPlayer().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+PLAYER_CONDITIONS, 	false,	false, "&7&oThe player condtions", "&a✎ Click here to change");
			i++;
		}

		if(sOp.getOptionWithWorld().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+WORLD_CONDITIONS, 	false,	false, "&7&oThe world condtions", "&a✎ Click here to change");
			i++;
		}

		if(sOp.getOptionWithTargetEntity().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+ENTITY_CONDITIONS, 	false,	false, "&7&oThe entity condtions", "&a✎ Click here to change");
			i++;
		}
		
		if(sOp.getOptionWithTargetBlock().contains(sOp)) {
			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+BLOCK_CONDITIONS, 	false,	false, "&7&oThe block condtions", "&a✎ Click here to change");
			i++;
		}
		
		if(sOp.getOptionWithOwner().contains(sOp) || sOp.getOptionWithPlayer().contains(sOp)) {
			if(SCore.hasExecutableBlocks) createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+PLACEHOLDERS_CONDITIONS, 	false,	false, "&7&oThe placeholders condtions", "&6For EB: &eowner = &aplayer &6& &etarget player = &atarget", "&a✎ Click here to change");
			else createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+PLACEHOLDERS_CONDITIONS, 	false,	false, "&7&oThe placeholders condtions", "&a✎ Click here to change");
			i++;
		}

		createItem(Material.PAPER,							1 , i, 	"&e&lEdition of messages", 	false,	false, "&8&oNOT IN GUI", "&7&oYou need to go in your item file", "&7&oBelow your condition exemple:", "&eIfSneaking: true", "&7&oAdd new line", "&eIfSneaking&e&lMsg&e: 'YOUR MESSAGE'", "&7&o empty= no message");
		i++;

		createItem(RED, 									1 , 18, 	"&4&l▶ &cBack to activator config", 	false, false);

		createItem(Material.BOOK, 							1 , 24, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+sObject.getID());
		createItem(Material.BOOK, 							1 , 25, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+sAct.getID());

		return;
	}

}
