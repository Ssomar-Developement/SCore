package com.ssomar.score.sobject.sactivator.menu.conditions;

import org.bukkit.Material;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.CustomConditions;
import com.ssomar.score.splugin.SPlugin;

public class CustomConditionsGUI extends ConditionGUIAbstract{
	
	public static final String IF_NEED_PLAYER_CONFIRMATION = "ifNeedPlayerConfirmation";
	public static final String IF_PLAYER_MUST_BE_ON_HIS_ISLAND = "ifPlayerMustBeOnHisIsland";
	
	public CustomConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sAct, CustomConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Custom Conditions", 3*9, sPlugin, sObject, sAct, detail);
		
		int i =0;
		//Main Options
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NEED_PLAYER_CONFIRMATION, 	false,	false, "&7&oThe player must double click?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_NEED_PLAYER_CONFIRMATION, conditions.isIfNeedPlayerConfirmation());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_PLAYER_MUST_BE_ON_HIS_ISLAND, 	false,	false, "&7&oThe player must be on his island?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_PLAYER_MUST_BE_ON_HIS_ISLAND, conditions.isIfPlayerMustBeOnHisIsland());
		
		createItem(RED, 				1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 				1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of custom conditions" );
		
		createItem(GREEN, 				1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of custom conditions" );
		
		createItem(Material.BOOK, 	1 , 24, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+sObject.getID());
		createItem(Material.BOOK, 	1 , 25, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+sAct.getID());
	}
}
