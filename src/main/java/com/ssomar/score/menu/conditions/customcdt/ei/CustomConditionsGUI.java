package com.ssomar.score.menu.conditions.customcdt.ei;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.CustomEIConditions;
import com.ssomar.score.splugin.SPlugin;

public class CustomConditionsGUI extends ConditionGUIAbstract{
	
	public static final String IF_NEED_PLAYER_CONFIRMATION = "ifNeedPlayerConfirmation";
	public static final String IF_OWNER_OF_THE_EI = "if owner of the EI";
	public static final String IF_NOT_OWNER_OF_THE_EI = "if not owner of the EI";
	public static final String IF_PLAYER_MUST_BE_ON_HIS_ISLAND = "ifPlayerMustBeOnHisIsland";
	public static final String IF_PLAYER_MUST_BE_ON_HIS_CLAIM = "if Player Must Be On His Claim";
	
	private CustomEIConditions conditions;
	
	public CustomConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sAct, CustomEIConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Custom Conditions", 3*9, sPlugin, sObject, sAct, detail);
		this.conditions = conditions;
		
		int i = 0;
		//Main Options
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NEED_PLAYER_CONFIRMATION, 	false,	false, "&7&oThe player must double click?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_NEED_PLAYER_CONFIRMATION, conditions.isIfNeedPlayerConfirmation());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_OWNER_OF_THE_EI, 	false,	false, "&7&oThe player must be the owner?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_OWNER_OF_THE_EI, conditions.isIfOwnerOfTheEI());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NOT_OWNER_OF_THE_EI, 	false,	false, "&7&oThe player must not be the owner?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_NOT_OWNER_OF_THE_EI, conditions.isIfNotOwnerOfTheEI());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_PLAYER_MUST_BE_ON_HIS_ISLAND, 	false,	false, "&7&oThe player must be on his island?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_PLAYER_MUST_BE_ON_HIS_ISLAND, conditions.isIfPlayerMustBeOnHisIsland());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_PLAYER_MUST_BE_ON_HIS_CLAIM, 	false,	false, "&7&oThe player must be on his claim or a friend claim?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_PLAYER_MUST_BE_ON_HIS_CLAIM, conditions.isIfPlayerMustBeOnHisClaim());
		
		createItem(RED, 				1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 				1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of custom conditions" );
		
		createItem(GREEN, 				1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of custom conditions" );
		
		createItem(WRITABLE_BOOK, 	1 , 22, 	"&aTo edit messages of Conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 	1 , 24, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+sObject.getID());
		createItem(Material.BOOK, 	1 , 25, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+sAct.getID());
	}

	public CustomEIConditions getConditions() {
		return conditions;
	}
}
