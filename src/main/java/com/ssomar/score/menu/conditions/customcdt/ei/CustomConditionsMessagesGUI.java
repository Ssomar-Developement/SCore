package com.ssomar.score.menu.conditions.customcdt.ei;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.CustomEIConditions;
import com.ssomar.score.splugin.SPlugin;

public class CustomConditionsMessagesGUI extends ConditionGUIAbstract{
	
	public CustomConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sAct, CustomEIConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Custom Conditions Messages", 3*9, sPlugin, sObject, sAct, detail, conditions);
	}

	@Override
	public void loadTheGUI() {
		CustomEIConditions conditions = (CustomEIConditions)this.getConditions();
		int i = 0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+CustomConditionsMessages.IF_NEED_PLAYER_CONFIRMATION_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(CustomConditionsMessages.IF_NEED_PLAYER_CONFIRMATION_MSG.name, conditions.getIfNeedPlayerConfirmationMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+CustomConditionsMessages.IF_OWNER_OF_THE_EI_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(CustomConditionsMessages.IF_OWNER_OF_THE_EI_MSG.name, conditions.getIfOwnerOfTheEIMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+CustomConditionsMessages.IF_NOT_OWNER_OF_THE_EI_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(CustomConditionsMessages.IF_NOT_OWNER_OF_THE_EI_MSG.name, conditions.getIfNotOwnerOfTheEIMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+CustomConditionsMessages.IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(CustomConditionsMessages.IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG.name, conditions.getIfPlayerMustBeOnHisIslandMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+CustomConditionsMessages.IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(CustomConditionsMessages.IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG.name, conditions.getIfPlayerMustBeOnHisClaimMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+CustomConditionsMessages.IF_PLAYER_MUST_BE_ON_HIS_PLOT_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(CustomConditionsMessages.IF_PLAYER_MUST_BE_ON_HIS_PLOT_MSG.name, conditions.getIfPlayerMustBeOnHisPlotMsg());
		
		createItem(RED, 				1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 				1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of custom conditions" );
		
		createItem(GREEN, 				1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of custom conditions" );
		
		createItem(WRITABLE_BOOK, 	1 , 22, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 	1 , 24, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getId());
		createItem(Material.BOOK, 	1 , 25, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}
	
	public enum CustomConditionsMessages{
		IF_NEED_PLAYER_CONFIRMATION_MSG ("ifNeedPlayerConfirmation message"),
		IF_OWNER_OF_THE_EI_MSG ("if owner of the EI message"),
		IF_NOT_OWNER_OF_THE_EI_MSG ("if not owner of the EI message"),
		IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG ("ifPlayerMustBeOnHisIsland message"),
		IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG ("if Player Must Be On His Claim message"),
		IF_PLAYER_MUST_BE_ON_HIS_PLOT_MSG ("if Player Must Be On His Plot message");
		
		public String name;

		CustomConditionsMessages(String name) {
			this.name = name;
		}
	}
}
