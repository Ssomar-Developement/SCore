package com.ssomar.score.menu.conditions.customcdt.ei;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.CustomEIConditions;
import com.ssomar.score.splugin.SPlugin;

public class CustomConditionsMessagesGUI extends ConditionGUIAbstract{
	
	public static final String IF_NEED_PLAYER_CONFIRMATION_MSG = "ifNeedPlayerConfirmation message";
	public static final String IF_OWNER_OF_THE_EI_MSG = "if owner of the EI message";
	public static final String IF_NOT_OWNER_OF_THE_EI_MSG = "if not owner of the EI message";
	public static final String IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG = "ifPlayerMustBeOnHisIsland message";
	public static final String IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG = "if Player Must Be On His Claim message";
	
	private CustomEIConditions conditions;
	
	public CustomConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sAct, CustomEIConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Custom Conditions Messages", 3*9, sPlugin, sObject, sAct, detail);
		this.conditions = conditions;
		
		int i = 0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_NEED_PLAYER_CONFIRMATION_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_NEED_PLAYER_CONFIRMATION_MSG, conditions.getIfNeedPlayerConfirmationMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_OWNER_OF_THE_EI_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_OWNER_OF_THE_EI_MSG, conditions.getIfOwnerOfTheEIMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_NOT_OWNER_OF_THE_EI_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_NOT_OWNER_OF_THE_EI_MSG, conditions.getIfNotOwnerOfTheEIMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG, conditions.getIfPlayerMustBeOnHisIslandMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG, conditions.getIfPlayerMustBeOnHisClaimMsg());
		
		createItem(RED, 				1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 				1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of custom conditions" );
		
		createItem(GREEN, 				1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of custom conditions" );
		
		createItem(WRITABLE_BOOK, 	1 , 22, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 	1 , 24, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+sObject.getID());
		createItem(Material.BOOK, 	1 , 25, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+sAct.getID());
	}

	public CustomEIConditions getConditions() {
		return conditions;
	}
}
